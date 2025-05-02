package hernanbosqued.frontend.ui

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import hernanbosqued.frontend.buildconfig.BuildKonfig
import hernanbosqued.frontend.platform_controller.di.DesktopPlatformControllerModule
import hernanbosqued.frontend.repository.di.RepositoryModule
import hernanbosqued.frontend.use_case.task.di.TaskUseCaseModule
import hernanbosqued.frontend.usecase.auth.di.DesktopAuthUseCaseModule
import hernanbosqued.frontend.viewmodel.auth.di.DesktopAuthViewModelModule
import hernanbosqued.frontend.viewmodel.task.di.TaskViewModelModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun main() =
    application {
        startKoin {
            modules(
                module {
                    single {
                        CoroutineScope(SupervisorJob() + Dispatchers.Default)
                    }
                },
                DesktopPlatformControllerModule.getModule(
                    clientId = BuildKonfig.clientId,
                    redirectUri = BuildKonfig.desktopRedirectUri,
                    scopes = listOf("profile", "email"),
                ),
                TaskViewModelModule.getModule(),
                RepositoryModule.getModule(BuildKonfig.apiUrl),
                TaskUseCaseModule.getModule(),
                DesktopAuthUseCaseModule.getModule(),
                DesktopAuthViewModelModule.getModule(),
            )
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "Clean Architecture Example",
        ) {
            App()
        }
    }
