package hernanbosqued.frontend.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import hernanbosqued.frontend.buildconfig.BuildKonfig
import hernanbosqued.frontend.repository.di.RepositoryModule
import hernanbosqued.frontend.ui.viewModels.AuthViewModel
import hernanbosqued.frontend.usecase.auth.di.WasmAuthUseCaseModule
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        modules(
            RepositoryModule.getModule(BuildKonfig.apiUrl),
            WasmAuthUseCaseModule.getModule(),
            module {
                single {
                    AuthViewModel(
                        authUseCase =
                            get {
                                parametersOf(
                                    BuildKonfig.clientId,
                                    BuildKonfig.redirectUri,
                                    listOf("profile", "email"),
                                )
                            },
                        coroutineScope = CoroutineScope(Dispatchers.Default),
                    )
                }
            },
        )
    }

    ComposeViewport(document.body!!) {
        var currentPage by remember { mutableStateOf(window.location.search) }
        LaunchedEffect(Unit) {
            window.onpopstate = {
                currentPage = window.location.search
            }
        }
        println("Renderizando para la ruta hash: ${window.location.search}")

        when {
            currentPage.contains("?auth") -> ProfileScreen()
            else -> App()
        }
    }
}

@Composable
fun ProfileScreen() {
    Text("Pantalla de Perfil")
}
