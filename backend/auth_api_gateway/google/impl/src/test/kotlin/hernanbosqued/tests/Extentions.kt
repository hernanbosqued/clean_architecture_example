package hernanbosqued.tests

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import io.ktor.server.testing.ApplicationTestBuilder
import io.ktor.server.testing.testApplication

fun Application.configureTestServer() {
    install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) {
        json()
    }
    routing {
        post("/success/null") {
            call.respond(ServerSuccess("Success!", null))
        }

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

fun testApplicationWithConfiguredClient(
    block: suspend ApplicationTestBuilder.(client: HttpClient) -> Unit
) = testApplication {
    application {
        configureTestServer()
    }

    val client = createClient {
        install(ContentNegotiation) {
            json()
        }
    }

    block(client)
}

suspend inline fun <reified T> HttpResponse.toDomainResponse(): DomainResponse {
    val response = this.body<Response>()

    return when (response) {
        is Response.Success -> DomainResponse.Success(response.message, response.decode<T>())
        is Response.Error -> DomainResponse.Error(response.message, response.code)
    }
}