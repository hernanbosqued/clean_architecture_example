package hernanbosqued.backend.dbController

import hernanbosqued.backend.db.ServerDatabase
import hernanbosqued.backend.domain.Controller
import hernanbosqued.backend.domain.IdTask
import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.domain.Task

class DbController(
    val db: ServerDatabase
) : Controller {

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
