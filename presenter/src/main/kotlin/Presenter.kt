package hernanbosqued.backend.presenter

import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.service.public.Service

class Presenter(
    private val service: Service
) {

    fun allTasks(): List<DTOIdTask> = service.allTasks().map { it.toDto() }

    fun taskById(taskId: Int?): Result<DTOIdTask, StatusCode> {
        if (taskId == null) return Result.Error(StatusCode.BadRequest)

        return when (val task = service.taskById(taskId)) {
                null -> Result.Error(StatusCode.NotFound)
                else -> Result.Success(task.toDto())
            }
    }

    fun taskByPriority(priorityStr: String?): Result<List<DTOIdTask>, StatusCode> {
        if (priorityStr.isNullOrBlank()) return Result.Error(StatusCode.BadRequest)

        val priority = Priority.entries.find { it.name.equals(priorityStr, ignoreCase = true) }

        return when (priority) {
            null -> Result.Error(StatusCode.BadRequest)
            else -> Result.Success(service.tasksByPriority(priority).map { it.toDto() })
        }
    }

    fun addTask(task: DTOTask): Result<Unit, StatusCode> {
            service.addTask(task)
            return Result.Success(Unit)
    }

    fun removeTask(taskId: Int?): Result<Unit, StatusCode> {
        if (taskId == null) return Result.Error(StatusCode.BadRequest)

        return when (service.removeTask(taskId)) {
            false -> Result.Error(StatusCode.NotFound)
            true -> Result.Success(Unit)
        }
    }
}