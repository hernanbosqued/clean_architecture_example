package hernanbosqued.backend.db_controller.sqlite

import hernanbosqued.backend.db.ServerDatabase
import hernanbosqued.domain.DbController
import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task

class SqliteDbController(
    val db: ServerDatabase,
) : DbController {
    private fun getDb(): List<IdTask> =
        db.taskQueries.selectAll().executeAsList().map {
            object : IdTask {
                override val id: Long = it.id
                override val name: String = it.name
                override val description: String = it.description
                override val priority: Priority = Priority.valueOf(it.priority)
            }
        }

    override fun allTasks(): List<IdTask> = getDb()

    override fun tasksByPriority(priority: Priority): List<IdTask> = getDb().filter { it.priority == priority }

    override fun taskById(taskId: Long) = getDb().find { it.id == taskId }

    override fun addTask(task: Task) {
        db.taskQueries.insert(task.name, task.description, task.priority.name)
    }

    override fun removeTask(id: Long): Boolean {
        db.taskQueries.remove(id)
        return true
    }
}
