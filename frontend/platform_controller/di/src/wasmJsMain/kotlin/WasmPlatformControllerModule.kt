package hernanbosqued.frontend.platform_controller.di

import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings
import hernanbosqued.frontend.platform_controller.impl.WasmPlatformControllerImpl
import hernanbosqued.frontend.usecase.auth.WasmPlatformController
import org.koin.dsl.module

object WasmPlatformControllerModule {
    fun getModules() = CommonPlatformControllerModule.getModule() +
        module {
            single<Settings> { StorageSettings() }
            single<WasmPlatformController> { WasmPlatformControllerImpl() }
        }
}
