package hernanbosqued.backend.db_controller.di

import app.cash.sqldelight.db.SqlDriver
import hernanbosqued.backend.db.ServerDatabase
import hernanbosqued.backend.db_controller.sqlite.DriverFactory
import hernanbosqued.backend.db_controller.sqlite.SqliteDbController
import hernanbosqued.domain.DbController
import org.koin.dsl.module

object DbControllerModule {
    fun getModule(path: String) =
        module {
            factory<SqlDriver> {
                DriverFactory.createDriver(path)
            }

            factory<ServerDatabase> {
                ServerDatabase(get())
            }

            single<DbController> {
                SqliteDbController(get())
            }
        }
}
