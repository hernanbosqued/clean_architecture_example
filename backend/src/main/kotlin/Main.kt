package hernanbosqued.backend

import com.auth0.jwk.JwkProviderBuilder
import hernanbosqued.backend.auth.di.AuthUseCaseModule
import hernanbosqued.backend.auth_api_gateway.google.di.GoogleAuthApiGatewayModule
import hernanbosqued.backend.db_controller.di.DbControllerModule
import hernanbosqued.backend.presenter.Presenter
import hernanbosqued.backend.presenter.Result
import hernanbosqued.backend.presenter.StatusCode
import hernanbosqued.backend.presenter.di.PresenterModule
import hernanbosqued.backend.use_case.db.di.DbUseCaseModule
import hernanbosqued.constants.Constants
import hernanbosqued.domain.dto.DTOAuthCodeRequest
import hernanbosqued.domain.dto.DTOAuthRefreshTokenRequest
import hernanbosqued.domain.dto.DTOTask
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.auth.principal
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import java.net.URL
import java.util.concurrent.TimeUnit

fun getModules(dbPath: String) =
    listOf(
        DbControllerModule.getModule(dbPath),
        DbUseCaseModule.getModule(),
        GoogleAuthApiGatewayModule.getModule(),
        AuthUseCaseModule.getModule(),
        PresenterModule.getModule(),
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

    val jwkProvider =
        JwkProviderBuilder(URL(Constants.GOOGLE_JWKS))
            .cached(1, 5, TimeUnit.MINUTES)
            .rateLimited(10, 10, TimeUnit.SECONDS)
            .build()

    install(Authentication) {
        jwt("auth-google") {
            verifier(jwkProvider, Constants.GOOGLE_ISSUER) {
                acceptLeeway(3)
                withAudience(Constants.GOOGLE_CLIENT)
            }

            validate { credential -> JWTPrincipal(credential.payload) }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }

    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Delete)
    }

    val presenter: Presenter by inject()

    routing {
        staticResources("static", "static")

        route("/auth") {
            post("/code") {
                val code = call.receive<DTOAuthCodeRequest>()
                val userData = presenter.getUserData(code)
                call.respond(userData)
            }

            post("/refresh_token") {
                val code = call.receive<DTOAuthRefreshTokenRequest>()
                val response = presenter.refreshToken(code)
                call.respond(response)
            }
        }

        authenticate("auth-google") {
            post("/totp") {
                call.withUserId { userId ->
                    val totp = call.receive<Int>()
                    call.respond(presenter.verifyTotp(userId, totp))
                }
            }

            route("/tasks") {
                get {
                    call.withUserId { userId ->
                        call.respond(presenter.allTasks(userId))
                    }
                }

                get("/priority/{priority}") {
                    call.withUserId { userId ->
                        val priorityAsText = call.parameters["priority"]?.lowercase()

                        when (val result = presenter.taskByPriority(userId, priorityAsText)) {
                            is Result.Success -> call.respond(result.value)
                            is Result.Error -> call.respond(result.error.map())
                        }
                    }
                }

                post {
                    call.withUserId { userId ->
                        val payload = call.receive<DTOTask>()
                        presenter.addTask(userId, payload)
                        call.respond(HttpStatusCode.Created)
                    }
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
}

private fun StatusCode.map(): HttpStatusCode {
    return when (this) {
        StatusCode.BadRequest -> HttpStatusCode.BadRequest
        StatusCode.NotFound -> HttpStatusCode.NotFound
    }
}

private suspend inline fun RoutingCall.withUserId(block: (String) -> Unit) {
    val principal = principal<JWTPrincipal>()
    val userId = principal?.payload?.getClaim("sub")?.asString()
    userId?.let {
        block(it)
    } ?: run {
        respond(HttpStatusCode.BadRequest, "User identifier (sub claim) missing or invalid in token")
    }
}