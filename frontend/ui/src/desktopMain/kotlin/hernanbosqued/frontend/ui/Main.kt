package hernanbosqued.frontend.ui

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import hernanbosqued.frontend.buildconfig.BuildKonfig
import hernanbosqued.frontend.repository.di.RepositoryModule
import hernanbosqued.frontend.use_case.task.di.TaskUseCaseModule
import hernanbosqued.frontend.usecase.auth.di.DesktopAuthUseCaseModule
import hernanbosqued.frontend.viewmodel.auth.di.DesktopAuthViewModelModule
import hernanbosqued.frontend.viewmodel.task.di.TaskViewModelModule
import org.koin.core.context.startKoin

fun main() =
    application {
        startKoin {
            modules(
                TaskViewModelModule.getModule(),
                RepositoryModule.getModule(BuildKonfig.apiUrl),
                TaskUseCaseModule.getModule(),
                DesktopAuthViewModelModule.getModule(),
                DesktopAuthUseCaseModule.getModule(BuildKonfig.clientId, BuildKonfig.desktopRedirectUri, listOf("profile", "email")),
            )
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "Clean Architecture Example",
        ) {
            App()
        }
    }
