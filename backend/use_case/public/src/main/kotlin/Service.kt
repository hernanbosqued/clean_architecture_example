package hernanbosqued.backend.service.public

import hernanbosqued.backend.domain.IdTask
import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.domain.Task

interface Service {
    fun allTasks(): List<IdTask>

    fun addTask(task: Task)

    fun removeTask(id: Int): Boolean

    fun tasksByPriority(priority: Priority): List<IdTask>

    fun taskById(taskId: Int): IdTask?
}
