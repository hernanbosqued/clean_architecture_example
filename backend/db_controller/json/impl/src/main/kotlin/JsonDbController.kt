package hernanbosqued.backend.db_controller.json

import com.google.gson.Gson
import hernanbosqued.domain.DbController
import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task
import java.io.File

class JsonDbController(path: String) : DbController {
    private val db = File(path)

    private fun getDb(): List<DAOTask> = Gson().fromJson(db.readText(), Array<DAOTask>::class.java).toList()

    override fun allTasks(userId: String): List<IdTask> = getDb().filter { it.userId == userId}

    override fun getMfaSecret(userId: String): String? {
        TODO("Not yet implemented")
    }

    override fun addMfaSecret(userId: String, mfaSecret: String) {
        TODO("Not yet implemented")
    }

    override fun tasksByPriority(userId: String, priority: Priority): List<IdTask> = getDb().filter { it.userId == userId && it.priority == priority }

    override fun addTask(userId: String, task: Task) {
        val allTasks =
            getDb().toMutableList().apply {
                add(
                    DAOTask(
                        id = this.maxOf { it.id } + 1,
                        userId = userId,
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
