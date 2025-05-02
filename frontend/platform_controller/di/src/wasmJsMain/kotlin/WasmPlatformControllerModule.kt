package hernanbosqued.frontend.platform_controller.di

import hernanbosqued.frontend.platform_controller.WasmPlatformController
import hernanbosqued.frontend.platform_controller.impl.WasmPlatformControllerImpl
import org.koin.dsl.module

object WasmPlatformControllerModule {
    fun getModule() = module {
        single<WasmPlatformController> {
            WasmPlatformControllerImpl()
        }
    }
}