package hernanbosqued.frontend.ui

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.buildconfig.BuildKonfig
import hernanbosqued.frontend.repository.di.RepositoryModule
import hernanbosqued.frontend.use_case.task.di.TaskUseCaseModule
import hernanbosqued.frontend.viewmodel.auth.LoginActions
import hernanbosqued.frontend.viewmodel.auth.di.AuthViewModelModule
import hernanbosqued.frontend.viewmodel.task.di.TaskViewModelModule
import io.ktor.http.ContentType
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module
import java.awt.Desktop
import java.net.URI

fun main() =
    application {
        startKoin {
            modules(
                module {
                    single {
                        CoroutineScope(SupervisorJob() + Dispatchers.Default)
                    }
                    single {
                        DesktopLoginActions(
                            clientId = BuildKonfig.clientId,
                            redirectUri = BuildKonfig.desktopRedirectUri,
                            scopes = listOf("profile", "email"),
                            frontendRepository = get()
                        )
                    }.bind(LoginActions::class)
                },
                TaskViewModelModule.getModule(),
                RepositoryModule.getModule(BuildKonfig.apiUrl),
                TaskUseCaseModule.getModule(),
                AuthViewModelModule.getModule()
            )
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "Clean Architecture Example",
        ) {
            App()
        }
    }

class DesktopLoginActions(
    clientId: String,
    redirectUri: String,
    scopes: List<String>,
    frontendRepository: FrontendRepository
) : LoginActionsBase(clientId, redirectUri, scopes, frontendRepository) {
    override suspend fun login() {
        userData.emit(openPageAndWaitForResponse())
    }

    suspend fun openPageAndWaitForResponse(): UserData {
        val localServer = LocalServer(8082)

        val authorizationUrl = generateAuthorizationUrl(clientId, redirectUri, scopes)

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI(authorizationUrl))
        }

        val code = localServer.waitForCode()
        val userData = sendAuthCode(code!!)
        return userData
    }

}

class LocalServer(port: Int) {
    private val codeDeferred = CompletableDeferred<String?>()

    private val server = embeddedServer(factory = Netty, port = port) {
        routing {
            get("/") {
                codeDeferred.complete(call.request.queryParameters["code"])
                call.respondText(
                    """
                        <html><body><h1>Autenticación Exitosa</h1><p>Puedes cerrar esta ventana.</p><script>window.close();</script></body></html>
                    """,
                    ContentType.Text.Html,
                )
            }
        }
    }.start(wait = false)

    suspend fun waitForCode(): String? = codeDeferred.await().also { server.stop() }
}
