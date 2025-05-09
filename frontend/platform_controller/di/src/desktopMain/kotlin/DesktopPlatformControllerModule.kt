package hernanbosqued.frontend.platform_controller.di

import CommonPlatformControllerModule
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import hernanbosqued.frontend.platform_controller.impl.DesktopPlatformControllerImpl
import hernanbosqued.frontend.usecase.auth.DesktopPlatformController
import org.koin.dsl.module
import java.util.prefs.Preferences

object DesktopPlatformControllerModule {
    fun getModules() = CommonPlatformControllerModule.getModule() +
            module {
                single<Settings> {
                    PreferencesSettings(Preferences.userRoot().node("hernanbosqued.frontend.platform_controller"))
                }
                single<DesktopPlatformController> { DesktopPlatformControllerImpl() }
            }
}
