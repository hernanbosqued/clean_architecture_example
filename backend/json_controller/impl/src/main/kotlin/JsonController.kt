package hernanbosqued.backend.jsonController

import com.google.gson.Gson
import hernanbosqued.backend.domain.Controller
import hernanbosqued.backend.domain.IdTask
import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.domain.Task
import java.io.File

class JsonController(path: String) : Controller {
    private val db = File(path)

    private fun getDb(): List<DAOTask> = Gson().fromJson(db.readText(), Array<DAOTask>::class.java).toList()

    override fun allTasks(): List<IdTask> = getDb()

    override fun tasksByPriority(priority: Priority): List<IdTask> = getDb().filter { it.priority == priority }

    override fun taskById(taskId: Long) = getDb().find { it.id == taskId }

    override fun addTask(task: Task) {
        val allTasks =
            getDb().toMutableList().apply {
                add(
                    DAOTask(
                        id = this.maxOf { it.id } + 1,
                        name = task.name,
                        description = task.description,
                        priority = task.priority,
                    ),
                )
            }

        with(db.writer()) {
            write(Gson().toJson(allTasks))
            close()
        }
    }

    override fun removeTask(id: Long): Boolean {
        val allTasks = getDb().toMutableList()

        return allTasks.removeIf { it.id == id }.also { response ->
            if (response) {
                with(db.writer()) {
                    write(Gson().toJson(allTasks))
                    close()
                }
            }
        }
    }
}
