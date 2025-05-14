package hernanbosqued.backend

import hernanbosqued.backend.auth.di.AuthUseCaseModule
import hernanbosqued.backend.auth_api_gateway.google.di.GoogleAuthApiGatewayModule
import hernanbosqued.backend.db_controller.di.DbControllerModule
import hernanbosqued.backend.presenter.Presenter
import hernanbosqued.backend.presenter.di.PresenterModule
import hernanbosqued.backend.use_case.db.di.DbUseCaseModule
import hernanbosqued.constants.Constants
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        port = 8081,
        module = { main(args[0]) },
    ).start(wait = true)
}

fun Application.main(path: String) {
    install(Koin) {
        modules(
            modules = listOf(
                DbControllerModule.getModule(path),
                DbUseCaseModule.getModule(),
                GoogleAuthApiGatewayModule.getModule(),
                AuthUseCaseModule.getModule(),
                PresenterModule.getModule(),
            )
        )
    }

    install(ContentNegotiation) {
        json()
    }

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
        addAuthRouting(presenter)
        addTaskRouting(presenter)
    }
}