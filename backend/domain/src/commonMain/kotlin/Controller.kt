package hernanbosqued.backend.domain

interface Controller {
    fun allTasks(): List<IdTask>

    fun addTask(task: Task)

    fun removeTask(id: Long): Boolean

    fun tasksByPriority(priority: Priority): List<IdTask>

    fun taskById(taskId: Long): IdTask?
}
