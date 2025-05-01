package hernanbosqued.backend.presenter

interface Presenter {
    fun allTasks(): List<DTOIdTask>

    fun taskById(taskId: Long?): Result<DTOIdTask, StatusCode>

    fun taskByPriority(priorityStr: String?): Result<List<DTOIdTask>, StatusCode>

    fun addTask(task: DTOTask): Result<Unit, StatusCode>

    fun removeTask(taskId: Long?): Result<Unit, StatusCode>

    suspend fun getUserData(code: DTOTokenRequest): DTOUserData
}
