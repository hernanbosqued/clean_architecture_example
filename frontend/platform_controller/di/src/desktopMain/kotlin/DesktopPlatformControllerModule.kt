package hernanbosqued.frontend.platform_controller.di

import PersistenceImpl
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import hernanbosqued.frontend.platform_controller.impl.DesktopPlatformControllerImpl
import hernanbosqued.frontend.usecase.auth.DesktopPlatformController
import hernanbosqued.frontend.usecase.auth.Persistence
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import java.util.prefs.Preferences

object DesktopPlatformControllerModule {

    fun getModule() = module {

        single {
            Json {
                ignoreUnknownKeys = true
                isLenient = true
                classDiscriminator = "type"
            }
        }

        single<Settings> {
            PreferencesSettings(Preferences.userRoot().node("hernanbosqued.frontend.platform_controller"))
        }

        single<Persistence> { PersistenceImpl(get(), get()) }
        single<DesktopPlatformController> { DesktopPlatformControllerImpl() }
    }
}
