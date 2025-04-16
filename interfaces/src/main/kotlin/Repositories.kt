import com.google.gson.Gson
import hernanbosqued.backend.entities.IdTask
import hernanbosqued.backend.entities.Priority
import hernanbosqued.backend.entities.Repository
import hernanbosqued.backend.entities.Task
import java.io.File

class DatabaseRepository : Repository {

    private val db = File("C:/Users/hernan/dev/backend/interfaces/src/main/resources/db.json")

    private fun getDb(): List<DAOTask> = Gson().fromJson(db.readText(), Array<DAOTask>::class.java).toList()

    override fun allTasks(): List<IdTask> = getDb()

    override fun tasksByPriority(priority: Priority): List<IdTask> = getDb().filter { it.priority == priority }

    override fun taskById(taskId: Int) = getDb().find { it.id == taskId }

    override fun addTask(task: Task) {
        val allTasks = getDb().toMutableList().apply {
            add(
                DAOTask(
                    id = this.maxOf { it.id } + 1,
                    name = task.name,
                    description = task.description,
                    priority = task.priority)
            )
        }

        with(db.writer()) {
            write(Gson().toJson(allTasks))
            close()
        }
    }

    override fun removeTask(id: Int): Boolean {
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
