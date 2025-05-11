package hernanbosqued.frontend.ui

import android.app.Application
import hernanbosqued.constants.Constants
import hernanbosqued.frontend.platform_controller.di.AndroidPlatformControllerModule
import hernanbosqued.frontend.repository.di.RepositoryModule
import hernanbosqued.frontend.use_case.auth.di.AndroidAuthUseCaseModule
import hernanbosqued.frontend.use_case.auth.di.DesktopAuthUseCaseModule
import hernanbosqued.frontend.use_case.task.di.TaskUseCaseModule
import hernanbosqued.frontend.viewmodel.auth.di.DesktopAuthViewModelModule
import hernanbosqued.frontend.viewmodel.task.di.TaskViewModelModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class AndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AndroidApplication)

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
                *AndroidPlatformControllerModule.getModules().toTypedArray(),
                AndroidAuthUseCaseModule.getModule(
                    clientId = Constants.GOOGLE_CLIENT,
                    redirectUri = Constants.DESKTOP_REDIRECT_URL,
                    scopes = listOf("profile", "email"),
                ),
                DesktopAuthViewModelModule.getModule(),
            )
        }
    }
}