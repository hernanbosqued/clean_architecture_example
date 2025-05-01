package hernanbosqued.frontend.repository

import hernanbosqued.backend.presenter.DTOIdTask
import hernanbosqued.backend.presenter.DTOTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.UserData

interface Repository {
    suspend fun allTasks(): List<DTOIdTask>

    suspend fun taskById(taskId: Int): DTOIdTask

    suspend fun taskByPriority(priority: Priority): List<DTOIdTask>

    suspend fun addTask(task: DTOTask)

    suspend fun removeTask(taskId: Int?)

    suspend fun sendAuthorizationCode(
        code: String,
        clientId: String,
        redirectUri: String,
    ): UserData
}
