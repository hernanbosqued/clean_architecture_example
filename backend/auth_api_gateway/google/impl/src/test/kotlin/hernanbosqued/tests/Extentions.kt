package hernanbosqued.tests

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

fun Application.configureTestServer() {
    install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) {
        json()
    }
    routing {
        post("/success/user") {
            val userProfile = UserProfile("id", "name")
            call.respond(ServerSuccess("Success!", userProfile))
        }

        post("/success/string") {
            call.respond(ServerSuccess("Success!", "String Content"))
        }

        post("/success/int") {
            call.respond(ServerSuccess("Success!", 1))
        }

        post("/error") {
            call.respond(Response.Error("Error", 1))
        }
    }
}

private fun ApplicationTestBuilder.configureTestClient(): HttpClient {
    val client = createClient {
        install(ContentNegotiation) {
            json(Json {
                serializersModule = SerializersModule {
                    polymorphic(Response::class) {
                        subclass(Response.Success::class)
                        subclass(Response.Error::class)
                    }
                }
            })
        }
    }
    return client
}

fun testApplicationWithConfiguredClient(
    block: suspend ApplicationTestBuilder.(client: HttpClient) -> Unit
) = testApplication {
    application {
        configureTestServer()
    }
    block(configureTestClient())
}