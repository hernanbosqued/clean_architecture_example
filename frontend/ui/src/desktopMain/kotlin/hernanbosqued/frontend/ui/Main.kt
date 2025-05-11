package hernanbosqued.frontend.ui

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import hernanbosqued.constants.Constants
import hernanbosqued.frontend.platform_controller.di.DesktopPlatformControllerModule
import hernanbosqued.frontend.repository.di.RepositoryModule
import hernanbosqued.frontend.use_case.auth.di.DesktopAuthUseCaseModule
import hernanbosqued.frontend.use_case.task.di.TaskUseCaseModule
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
                TaskViewModelModule.getModule(),
                RepositoryModule.getModule(
                    apiUrl = Constants.API_URL
                ),
                TaskUseCaseModule.getModule(),
                *DesktopPlatformControllerModule.getModules().toTypedArray(),
                DesktopAuthUseCaseModule.getModule(
                    clientId = Constants.GOOGLE_CLIENT,
                    redirectUri = Constants.DESKTOP_REDIRECT_URL,
                    scopes = listOf("profile", "email"),
                ),
                DesktopAuthViewModelModule.getModule(),
            )
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "Clean Architecture Example",
        ) {
            window.minimumSize = java.awt.Dimension(400, 300)

            App()
        }
    }
