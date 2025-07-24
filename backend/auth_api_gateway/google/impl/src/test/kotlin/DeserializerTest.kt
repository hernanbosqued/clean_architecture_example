import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ClientContentNegotiation
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation as ServerContentNegotiation

@Serializable(with = MessageSerializer::class)
interface Message {
    val message: String
}

@Serializable(with = SuccessResponseSerializer::class)
data class SuccessResponse(
    override val message: String,
    val content: String
) : Message

@Serializable
data class ErrorResponse(
    override val message: String,
    val code: Int
) : Message

object SuccessResponseSerializer : KSerializer<SuccessResponse> {
    // 1. Describe la estructura de la clase
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("SuccessResponse") {
        element<String>("message")
        element<String>("content")
    }

    // 2. Define cómo convertir el objeto a JSON
    override fun serialize(encoder: Encoder, value: SuccessResponse) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.message)
            encodeStringElement(descriptor, 1, value.content)
        }
    }

    // 3. Define cómo convertir el JSON a un objeto
    override fun deserialize(decoder: Decoder): SuccessResponse {
        return decoder.decodeStructure(descriptor) {
            var message: String? = null
            var content: String? = null
            // Lee los campos en cualquier orden
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> message = decodeStringElement(descriptor, 0)
                    1 -> content = decodeStringElement(descriptor, 1)
                    -1 -> break // Fin de la estructura
                    else -> throw IllegalStateException("Unexpected index: $index")
                }
            }
            // Construye el objeto final
            SuccessResponse(
                message = requireNotNull(message) { "Field 'message' is missing" },
                content = requireNotNull(content) { "Field 'content' is missing" }
            )
        }
    }
}

object MessageSerializer : JsonContentPolymorphicSerializer<Message>(Message::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Message> {
        val jsonObject = element.jsonObject
        return when {
            "content" in jsonObject -> SuccessResponse.serializer()
            "code" in jsonObject -> ErrorResponse.serializer()
            else -> throw IllegalArgumentException("Unknown message type: $element")
        }
    }
}

// --- Módulo de Aplicación para el Test ---

fun Application.configureTestServer() {
    install(ServerContentNegotiation) {
        json(Json {
            prettyPrint = true
            serializersModule = SerializersModule {
                polymorphic(Message::class) {
                    subclass(SuccessResponse::class)
                    subclass(ErrorResponse::class)
                }
            }
        })
    }
    routing {
        post("/error") {
            try {
                val message = call.receive<Message>()
                call.respondText("Received message with ID: ${message.message}")
            } catch (e: Exception) {
                // Usar la causa raíz para obtener el mensaje de error más específico
                val rootCause = e.cause ?: e
                call.respond(HttpStatusCode.BadRequest, rootCause.message ?: "Bad Request")
            }
        }

        post("/messages") {
            try {
                val message = call.receive<Message>()
                call.respondText("Received message with ID: ${message.message}")
            } catch (e: Exception) {
                // Usar la causa raíz para obtener el mensaje de error más específico
                val rootCause = e.cause ?: e
                call.respond(HttpStatusCode.BadRequest, rootCause.message ?: "Bad Request")
            }
        }
        get("/messages") {
            val messages: List<Message> = listOf(
                SuccessResponse("text-001", "Hola, este es un mensaje de prueba."),
                ErrorResponse("image-002", 1)
            )
            call.respond(messages)
        }
    }
}

// --- Función de Ayuda para los Tests (Mejor Práctica) ---

fun testApplicationWithConfiguredClient(
    block: suspend ApplicationTestBuilder.(client: HttpClient) -> Unit
) = testApplication {
    application {
        configureTestServer()
    }
    val client = createClient {
        install(ClientContentNegotiation) {
            json(Json {
                serializersModule = SerializersModule {
                    polymorphic(Message::class) {
                        subclass(SuccessResponse::class)
                        subclass(ErrorResponse::class)
                    }
                }
            })
        }
    }
    block(client)
}


// --- Clase de Test para Ktor 3.x (Limpia y Robusta) ---

class MessageSerializationTest {

    @Test // <-- Esta anotación ahora es de org.junit.Test y funcionará correctamente
    fun `should deserialize TextMessage from POST request`() = testApplicationWithConfiguredClient { client ->
        val messageToSend = SuccessResponse("text-test-1", "Hello from unit test!")
        val response = client.post("/messages") {
            contentType(ContentType.Application.Json)
            setBody(messageToSend)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Received message with ID: text-test-1", response.bodyAsText())
    }

    @Test
    fun `should deserialize ImageMessage from POST request`() = testApplicationWithConfiguredClient { client ->
        val messageToSend = ErrorResponse(
            message = "image-test-2",
            code = 1
        )
        val response = client.post("/messages") {
            contentType(ContentType.Application.Json)
            setBody(messageToSend)
        }
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Received message with ID: image-test-2", response.bodyAsText())
    }

    @Test
    fun `should serialize and deserialize multiple polymorphic messages from GET request`() = testApplicationWithConfiguredClient { client ->
        val response = client.get("/messages")
        assertEquals(HttpStatusCode.OK, response.status)
        val receivedMessages = response.body<List<Message>>()
        assertEquals(2, receivedMessages.size)
        assertIs<SuccessResponse>(receivedMessages[0])
        assertIs<ErrorResponse>(receivedMessages[1])
    }

    @Test
    fun `should return bad request for unknown message type`() = testApplicationWithConfiguredClient { client ->
        val unknownMessageJson = """
                {
                  "id": "unknown-type",
                  "bad_field": "This should fail"
                }
            """.trimIndent()
        val response = client.post("/messages") {
            contentType(ContentType.Application.Json)
            setBody(unknownMessageJson)
        }
        assertEquals(HttpStatusCode.BadRequest, response.status)
        assertTrue(response.bodyAsText().contains("Unknown message type"))
    }
}