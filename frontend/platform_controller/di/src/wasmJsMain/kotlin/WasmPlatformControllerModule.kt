package hernanbosqued.frontend.platform_controller.di

import PersistenceImpl
import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings
import hernanbosqued.frontend.platform_controller.impl.WasmPlatformControllerImpl
import hernanbosqued.frontend.usecase.auth.Persistence
import hernanbosqued.frontend.usecase.auth.WasmPlatformController
import kotlinx.serialization.json.Json
import org.koin.dsl.module

object WasmPlatformControllerModule {
    fun getModule() = module {
        single {
            Json {
                ignoreUnknownKeys = true
                isLenient = true
                classDiscriminator = "type"
            }
        }

        single<Settings> { StorageSettings() }
        single<Persistence> { PersistenceImpl(get(), get()) }
        single<WasmPlatformController> { WasmPlatformControllerImpl() }
    }
}