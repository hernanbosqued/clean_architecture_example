package hernanbosqued.domain

interface DbController {
    fun addTask(userId: String, task: Task)
    fun removeTask(id: Long): Boolean
    fun tasksByPriority(userId: String, priority: Priority): List<IdTask>
    fun allTasks(userId: String): List<IdTask>

    fun getMfaSecret(userId: String): String?
    fun addMfaSecret(userId: String, mfaSecret: String)
}
