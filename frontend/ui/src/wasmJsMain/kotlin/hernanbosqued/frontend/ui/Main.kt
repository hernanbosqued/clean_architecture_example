package hernanbosqued.frontend.ui

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import hernanbosqued.frontend.buildconfig.BuildKonfig
import hernanbosqued.frontend.repository.di.RepositoryModule
import hernanbosqued.frontend.ui.viewModels.AuthViewModel
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import hernanbosqued.frontend.usecase.auth.impl.WasmAuthUseCaseImpl
import kotlinx.browser.document
import kotlinx.browser.window
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    println("Atlanta 1")

    startKoin {
        modules(
            RepositoryModule.getModule(BuildKonfig.apiUrl),
            module {
                single {
                    WasmAuthUseCaseImpl(BuildKonfig.clientId, BuildKonfig.webRedirectUri, listOf("profile", "email"), get())
                }.bind(AuthUseCase::class).also { println("Atlanta 2") }

                single {
                    WasmAuthViewModel(get())
                }.bind(AuthViewModel::class).also { println("Atlanta 3") }
            },
        )
    }

    ComposeViewport(document.body!!) {
        App(window.location.search)
    }
}
