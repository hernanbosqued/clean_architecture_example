package hernanbosqued.backend.presenter

interface Presenter {
    fun allTasks(): List<DTOIdTask>
    fun taskById(taskId: Int?): Result<DTOIdTask, StatusCode>
    fun taskByPriority(priorityStr: String?): Result<List<DTOIdTask>, StatusCode>
    fun addTask(task: DTOTask): Result<Unit, StatusCode>
    fun removeTask(taskId: Int?): Result<Unit, StatusCode>
}