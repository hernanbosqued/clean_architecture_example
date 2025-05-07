package hernanbosqued.domain

interface DbController {
    fun addTask(task: Task)

    fun removeTask(id: Long): Boolean

    fun tasksByPriority(userId: String, priority: Priority): List<IdTask>

    fun allTasks(userId: String): List<IdTask>
}
