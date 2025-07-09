package hernanbosqued.domain

interface FrontendRepository {
    suspend fun allTasks(): List<IdTask>
    suspend fun taskByPriority(priority: Priority): List<IdTask>
    suspend fun addTask(task: Task)
    suspend fun removeTask(taskId: Long)
    suspend fun getUserData(authorizationCode: String, clientId: String, redirectUri: String): UserData
    suspend fun refreshToken(refreshToken: String): UserData
    suspend fun sendTotp(totp: Int): Boolean
}
