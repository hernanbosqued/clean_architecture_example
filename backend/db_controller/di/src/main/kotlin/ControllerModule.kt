package hernanbosqued.backend.controller.di

import app.cash.sqldelight.db.SqlDriver
import hernanbosqued.backend.db.ServerDatabase
import hernanbosqued.backend.db.ServerDatabase.Companion.invoke
import hernanbosqued.backend.dbController.DbController
import hernanbosqued.backend.dbController.DriverFactory
import hernanbosqued.backend.domain.Controller
import org.koin.dsl.module
import kotlin.math.sin

object ControllerModule {
    fun getModule(path: String) =
        module {
            factory<SqlDriver> {
                DriverFactory.createDriver(path)
            }

            factory<ServerDatabase> {
                ServerDatabase(get())
            }

            single<Controller> {
                DbController(get())
            }
        }
}
