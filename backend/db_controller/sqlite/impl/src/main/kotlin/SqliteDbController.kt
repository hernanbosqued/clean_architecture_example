package hernanbosqued.backend.db_controller.sqlite

import hernanbosqued.backend.db.ServerDatabase
import hernanbosqued.domain.DbController
import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task

class SqliteDbController(
    val db: ServerDatabase,
) : DbController {
    private fun getDb(userId: String): List<IdTask> =
        db.taskQueries.selectByUserId(userId).executeAsList().map {
            object : IdTask {
                override val id: Long = it.id
                override val name: String = it.name
                override val description: String = it.description
                override val priority: Priority = Priority.valueOf(it.priority)
            }
        }

    override fun allTasks(userId: String): List<IdTask> = getDb(userId)

    override fun tasksByPriority(userId: String, priority: Priority): List<IdTask> = getDb(userId).filter { it.priority == priority }

    override fun addTask(userId: String, task: Task) {
        db.taskQueries.insert(userId, task.name, task.description, task.priority.name)
    }

    override fun removeTask(id: Long): Boolean {
        db.taskQueries.remove(id)
        return true
    }

    override fun getMfaSecret(userId: String): String? = db.mfaQueries.selectByUserId(userId).executeAsOneOrNull()?.mfaSecret

    override fun addMfaSecret(userId: String, mfaSecret: String) = db.mfaQueries.insert(userId, mfaSecret)
}
