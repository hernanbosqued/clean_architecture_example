package hernanbosqued.frontend.platform_controller.di

import hernanbosqued.frontend.platform_controller.DesktopPlatformController
import hernanbosqued.frontend.platform_controller.impl.DesktopPlatformControllerImpl
import org.koin.dsl.module

object DesktopPlatformControllerModule {
    fun getModule() = module {
        single<DesktopPlatformController> {
            DesktopPlatformControllerImpl()
        }
    }
}