package hernanbosqued.backend.use_case.db

import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task

interface DbUseCase {
    fun allTasks(): List<IdTask>

    fun addTask(task: Task)

    fun removeTask(id: Long): Boolean

    fun tasksByPriority(priority: Priority): List<IdTask>

    fun taskById(taskId: Long): IdTask?
}
