package hernanbosqued.backend

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import hernanbosqued.backend.config.Constants
import hernanbosqued.backend.controller.di.ControllerModule
import hernanbosqued.backend.presenter.DTOTask
import hernanbosqued.backend.presenter.DTOUserData
import hernanbosqued.backend.presenter.Presenter
import hernanbosqued.backend.presenter.Result
import hernanbosqued.backend.presenter.StatusCode
import hernanbosqued.backend.presenter.TokenRequest
import hernanbosqued.backend.presenter.TokenResponse
import hernanbosqued.backend.presenter.di.PresenterModule
import hernanbosqued.backend.service.di.ServiceModule
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ContentNegotiationClient

fun getModules(dbPath: String) =
    listOf(
        ControllerModule.getModule(dbPath),
        PresenterModule.getModule(),
        ServiceModule.getModule(),
        module {
            factory {
                HttpClient {
                    install(plugin = ContentNegotiationClient) {
                        json()
                    }
                }
            }
        },
    )

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        port = 8081,
        module = { main(args[0]) },
    ).start(wait = true)
}

fun Application.main(path: String) {
    install(Koin) {
        modules(getModules(path))
    }

    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Delete)
    }

    val presenter: Presenter by inject()
    val httpClient: HttpClient by inject()

    routing {
        staticResources("static", "static")

        post("/code") {
            val code = call.receive<TokenRequest>()
            val secret = Constants.GOOGLE_SECRET
            val tokenResponse = exchangeCodeForTokens(httpClient, code.clientId, secret, code.redirectUri, code.authorizationCode)
            val userData = extractUserDataFromIdToken(tokenResponse)
            call.respond(userData)
        }

        route("/tasks") {
            get {
                call.respond(presenter.allTasks())
            }

            get("/id/{taskId}") {
                val taskId = call.parameters["taskId"]?.toLongOrNull()

                when (val result = presenter.taskById(taskId)) {
                    is Result.Success -> call.respond(result.value)
                    is Result.Error -> call.respond(result.error.map())
                }
            }

            get("/priority/{priority}") {
                val priorityAsText = call.parameters["priority"]?.lowercase()

                when (val result = presenter.taskByPriority(priorityAsText)) {
                    is Result.Success -> call.respond(result.value)
                    is Result.Error -> call.respond(result.error.map())
                }
            }

            post {
                val task = call.receive<DTOTask>()
                presenter.addTask(task)
                call.respond(HttpStatusCode.Created)
            }

            delete("/{taskId}") {
                val taskId = call.parameters["taskId"]?.toLongOrNull()
                when (val result = presenter.removeTask(taskId)) {
                    is Result.Success -> call.respond(HttpStatusCode.Accepted)
                    is Result.Error -> call.respond(result.error.map())
                }
            }
        }
    }
}

private fun StatusCode.map(): HttpStatusCode {
    return when (this) {
        StatusCode.BadRequest -> HttpStatusCode.BadRequest
        StatusCode.NotFound -> HttpStatusCode.NotFound
    }
}

suspend fun exchangeCodeForTokens(
    client: HttpClient,
    clientId: String,
    clientSecret: String,
    redirectUri: String,
    authorizationCode: String,
): TokenResponse {
    val response =
        client.post("https://oauth2.googleapis.com/token") {
            setBody(
                FormDataContent(
                    Parameters.build {
                        append("code", authorizationCode)
                        append("client_id", clientId)
                        append("client_secret", clientSecret)
                        append("redirect_uri", redirectUri)
                        append("grant_type", "authorization_code")
                    },
                ),
            )
            contentType(ContentType.Application.FormUrlEncoded)
        }
    if (response.status.isSuccess()) {
        return response.body()
    } else {
        println("Error al intercambiar el código por tokens: ${response.status} - ${response.bodyAsText()}")
        throw RuntimeException()
    }
}

fun extractUserDataFromIdToken(tokenResponse: TokenResponse): DTOUserData {
    return try {
        val jwt: DecodedJWT = JWT.decode(tokenResponse.id_token)
        val claims = jwt.claims

        val name = claims["name"]?.asString()
        val email = claims["email"]?.asString()
        val picture = claims["picture"]?.asString()

        DTOUserData(name!!, email!!, picture!!)
    } catch (e: Exception) {
        println("Error al decodificar el ID token: ${e.message}")
        throw RuntimeException()
    }
}
