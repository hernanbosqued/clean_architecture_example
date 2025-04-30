package hernanbosqued.frontend.repository

import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.presenter.DTOIdTask
import hernanbosqued.backend.presenter.DTOTask

interface Repository {
    suspend fun allTasks(): List<DTOIdTask>

    suspend fun taskById(taskId: Int): DTOIdTask

    suspend fun taskByPriority(priority: Priority): List<DTOIdTask>

    suspend fun addTask(task: DTOTask)

    suspend fun removeTask(taskId: Int?)
}
