package hernanbosqued.frontend.ui

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import hernanbosqued.constants.Constants
import hernanbosqued.frontend.platform_controller.di.WasmPlatformControllerModule
import hernanbosqued.frontend.repository.di.RepositoryModule
import hernanbosqued.frontend.use_case.auth.di.WasmAuthUseCaseModule
import hernanbosqued.frontend.use_case.task.di.TaskUseCaseModule
import hernanbosqued.frontend.viewmodel.auth.WasmAuthViewModel
import hernanbosqued.frontend.viewmodel.auth.di.WasmAuthViewModelModule
import hernanbosqued.frontend.viewmodel.task.di.TaskViewModelModule
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.context.startKoin
import org.koin.dsl.module

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(
            module {
                single {
                    CoroutineScope(SupervisorJob() + Dispatchers.Default)
                }
            },
            WasmPlatformControllerModule.getModule(),
            TaskViewModelModule.getModule(),
            RepositoryModule.getModule(Constants.API_URL),
            TaskUseCaseModule.getModule(),
            WasmAuthViewModelModule.getModule(),
            WasmAuthUseCaseModule.getModule(
                clientId = Constants.GOOGLE_CLIENT,
                redirectUri = Constants.WEB_REDIRECT_URL,
                scopes = listOf("profile", "email"),
            ),
        )
    }

    ComposeViewport(document.body!!) {
        val authViewModel = koinInject<WasmAuthViewModel>()
        val coroutineScope = rememberCoroutineScope()

        if (window.location.search.isBlank().not()) {
            coroutineScope.launch {
                authViewModel.setUserData(getAuthCodeFromQuerystring(window.location.search))
                window.history.replaceState(null, document.title, window.location.pathname)
            }
        }

        App()
    }
}
