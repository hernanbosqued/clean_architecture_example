package hernanbosqued.backend.dbController

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import hernanbosqued.backend.db.ServerDatabase
import java.io.File

object DriverFactory {
    private fun String.checkFileExists(): Boolean = File(this).exists()

    fun createDriver(path: String): SqlDriver {
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:$path")
        if (path.checkFileExists().not()) {
            ServerDatabase.Schema.create(driver)
        }
        return driver
    }
}
