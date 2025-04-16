package hernanbosqued.backed.presenter

import hernanbosqued.backend.entities.Priority
import hernanbosqued.backend.use_cases.UseCase


class Presenter(
    private val useCase: UseCase
) {
    fun allTasks(): List<DTOIdTask> = useCase.allTasks().map { it.toDto() }

    fun taskById(taskId: Int?): Result<DTOIdTask, StatusCode> {
        if (taskId == null) return Result.Error(StatusCode.BadRequest)

        return when (val task = useCase.taskById(taskId)) {
                null -> Result.Error(StatusCode.NotFound)
                else -> Result.Success(task.toDto())
            }
    }

    fun taskByPriority(priorityStr: String?): Result<List<DTOIdTask>, StatusCode> {
        if (priorityStr.isNullOrBlank()) return Result.Error(StatusCode.BadRequest)

        val priority = Priority.entries.find { it.name.equals(priorityStr, ignoreCase = true) }

        return when (priority) {
            null -> Result.Error(StatusCode.BadRequest)
            else -> Result.Success(useCase.tasksByPriority(priority).map { it.toDto() })
        }
    }

    fun addTask(task: DTOTask): Result<Unit, StatusCode> {
            useCase.addTask(task)
            return Result.Success(Unit)
    }

    fun removeTask(taskId: Int?): Result<Unit, StatusCode> {
        if (taskId == null) return Result.Error(StatusCode.BadRequest)

        return when (useCase.removeTask(taskId)) {
            false -> Result.Error(StatusCode.NotFound)
            true -> Result.Success(Unit)
        }
    }
}