package hernanbosqued.backend.controller.di

import hernanbosqued.backend.controller.JsonController
import hernanbosqued.backend.domain.Controller
import org.koin.dsl.module

object ControllerModule {
    fun getModule(path: String) =
        module {
            single<Controller> {
                JsonController(path)
            }
        }
}
