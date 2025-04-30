package hernanbosqued.frontend.ui

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import hernanbosqued.frontend.buildconfig.BuildKonfig
import hernanbosqued.frontend.repository.di.RepositoryModule
import hernanbosqued.frontend.ui.viewModels.AuthViewModel
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import hernanbosqued.frontend.usecase.auth.impl.DesktopAuthUseCaseImpl
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

fun main() =
    application {
        startKoin {
            modules(
                RepositoryModule.getModule(BuildKonfig.apiUrl),
                module {
                    single {
                        DesktopAuthUseCaseImpl(BuildKonfig.clientId, BuildKonfig.desktopRedirectUri, listOf("profile", "email"), get())
                    }.bind(AuthUseCase::class)

                    single<AuthViewModel> {
                        DesktopAuthViewModel(desktopAuthUseCaseImpl = get())
                    }
                },
            )
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "Clean Architecture Example",
        ) {
            App()
        }
    }
