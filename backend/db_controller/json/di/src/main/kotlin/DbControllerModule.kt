package hernanbosqued.backend.db_controller.di

import hernanbosqued.backend.db_controller.json.JsonDbController
import hernanbosqued.domain.DbController
import org.koin.dsl.module

object DbControllerModule {
    fun getModule(path: String) =
        module {
            single<DbController> {
                JsonDbController(get())
            }
        }
}
