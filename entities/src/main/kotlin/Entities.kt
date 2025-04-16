package hernanbosqued.backend.entities

enum class Priority {
    Low, Medium, High, Vital
}

interface Id{
    val id: Int
}

interface Task{
    val name: String
    val description: String
    val priority: Priority
}

interface IdTask: Id, Task

interface Repository{
    fun allTasks(): List<IdTask>
    fun addTask(task: Task)
    fun removeTask(id: Int): Boolean
    fun tasksByPriority(priority: Priority): List<IdTask>
    fun taskById(taskId: Int): IdTask?
}