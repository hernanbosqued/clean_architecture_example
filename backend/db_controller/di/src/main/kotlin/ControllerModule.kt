package hernanbosqued.backend.controller.di

import hernanbosqued.backend.db_controller.DbController
import hernanbosqued.backend.domain.Controller
import org.koin.dsl.module

object ControllerModule {
    fun getModule() =
        module {
            single<Controller> {
                DbController()
            }
        }
}
