package hernanbosqued.frontend.repository

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*

class Repository() {
    private val client = HttpClient{
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }

    suspend fun greeting(): String {
        val response: HttpResponse = client.get("http://localhost:8081/tasks")
        return response.bodyAsText()
    }
}

