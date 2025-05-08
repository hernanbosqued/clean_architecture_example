package hernanbosqued.backend.use_case.db

import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task

interface DbUseCase {
    fun allTasks(userId: String): List<IdTask>

    fun addTask(userId: String, task: Task)

    fun removeTask(id: Long): Boolean

    fun tasksByPriority(userId: String, priority: Priority): List<IdTask>
}
