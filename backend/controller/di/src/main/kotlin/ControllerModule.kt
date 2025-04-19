package hernanbosqued.backend.controller.di

import hernanbosqued.backend.domain.Controller
import hernanbosqued.backend.controller.JsonController
import org.koin.dsl.module

object ControllerModule{
    fun getModule(path: String) = module {
        single<Controller> {
            JsonController(path)
        }
    }
}
