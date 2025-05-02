package hernanbosqued.frontend.ui

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import hernanbosqued.domain.FrontendRepository
import hernanbosqued.frontend.buildconfig.BuildKonfig
import hernanbosqued.frontend.repository.di.RepositoryModule
import hernanbosqued.frontend.use_case.task.di.TaskUseCaseModule
import hernanbosqued.frontend.viewmodel.auth.LoginActions
import hernanbosqued.frontend.viewmodel.auth.di.AuthViewModelModule
import hernanbosqued.frontend.viewmodel.task.di.TaskViewModelModule
import io.ktor.http.Url
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    println("Atlanta 1")

    startKoin {
        modules(
            module {
                single {
                    CoroutineScope(SupervisorJob() + Dispatchers.Default)
                }
                single {
                    WasmLoginActions(
                        clientId = BuildKonfig.clientId,
                        redirectUri = BuildKonfig.webRedirectUri,
                        scopes = listOf("profile", "email"),
                        frontendRepository = get()
                    )
                }.bind(LoginActions::class)
            },
            TaskViewModelModule.getModule(),
            RepositoryModule.getModule(BuildKonfig.apiUrl),
            TaskUseCaseModule.getModule(),
            AuthViewModelModule.getModule(),
        )
    }

    ComposeViewport(document.body!!) {
        val loginActions = koinInject<WasmLoginActions>()
        val coroutineScope = rememberCoroutineScope()

        if (window.location.search.isBlank().not()) {
            coroutineScope.launch {
                loginActions.setUserData(window.location.search)
            }
        }

        App()
    }
}

class WasmLoginActions(
    clientId: String,
    redirectUri: String,
    scopes: List<String>,
    frontendRepository: FrontendRepository
) : LoginActionsBase(clientId, redirectUri, scopes, frontendRepository) {

    override suspend fun login() {
        val authorizationUrl = generateAuthorizationUrl(clientId, redirectUri, scopes)
        window.location.href = authorizationUrl
    }

    suspend fun setUserData(urlString: String) {
        val fullUrlString = "http://dummy.com$urlString"
        val url = Url(fullUrlString)
        val params = url.parameters
        val authCode = params["code"]
        userData.emit(sendAuthCode(authCode!!))
    }
}