package hernanbosqued.frontend.platform_controller.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import hernanbosqued.frontend.usecase.auth.AndroidPlatformController
import org.koin.dsl.module

object AndroidPlatformControllerModule {
    fun getModules() = CommonPlatformControllerModule.getModule() +
            module {
                single<Settings> {
                    SharedPreferencesSettings(get<Context>().getSharedPreferences("persistence", 0))
                }
                single<AndroidPlatformController> { AndroidPlatformControllerImpl() }
            }
}
