package hernanbosqued.domain

import kotlinx.coroutines.flow.StateFlow

interface FrontendRepository {
    val userData: StateFlow<UserData?>

    suspend fun allTasks(): List<IdTask>

    suspend fun taskById(taskId: Int): IdTask

    suspend fun taskByPriority(priority: Priority): List<IdTask>

    suspend fun addTask(task: Task)

    suspend fun removeTask(taskId: Int?)

    suspend fun sendAuthorizationCode(code: String, clientId: String, redirectUri: String)

    suspend fun refreshToken(refreshToken: String): UserData

    suspend fun logout()
}
