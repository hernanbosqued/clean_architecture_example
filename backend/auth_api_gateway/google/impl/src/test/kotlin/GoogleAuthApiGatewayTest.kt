import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import org.junit.Assert.assertEquals
import org.junit.Test

class GoogleAuthApiGatewayTest {

    @Serializable
    data class UserInfo(val id: String, val name: String)

    class MyApiService(private val httpClient: HttpClient) {

        suspend fun fetchUserInfo(userId: String): UserInfo? {
            return try {
                httpClient.get("https://api.example.com/users/$userId").body()
            } catch (e: Exception) {
                // En un caso real, manejarías el error de forma más específica
                null
            }
        }
    }


    @Test
    fun `fetchUserInfo debe devolver UserInfo cuando la API responde 200 OK`() = runTest {
        // --- Arrange (Preparar) ---

        // 1. Creamos el MockEngine. Este es el corazón de nuestro mock.
        val mockEngine = MockEngine { request ->
            // 2. Verificamos que la petición que llega es la que esperamos.
            assertEquals("https://api.example.com/users/123", request.url.toString())
            assertEquals(HttpMethod.Get, request.method)

            // 3. Respondemos con los datos que queremos simular.
            respond(
                content = """{"id":"123", "name":"Juan Perez"}""", // El JSON que la API real devolvería
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        // 4. Creamos una instancia de HttpClient, pasándole nuestro motor mockeado.
        val mockHttpClient = HttpClient(mockEngine) {
            // Es i
            // mportante configurar la serialización también en el cliente de test
            // si la clase que pruebas espera un objeto deserializado.
            install(ContentNegotiation) {
                json()
            }
        }

        // 5. Creamos la instancia de la clase que vamos a probar (System Under Test).
        val apiService = MyApiService(mockHttpClient)

        // --- Act (Actuar) ---

        // 6. Llamamos al método que queremos probar.
        val result = apiService.fetchUserInfo("123")

        // --- Assert (Verificar) ---

        // 7. Comprobamos que el resultado es el que esperamos.
        val expectedUser = UserInfo(id = "123", name = "Juan Perez")
        assertEquals(expectedUser, result)
    }
}
