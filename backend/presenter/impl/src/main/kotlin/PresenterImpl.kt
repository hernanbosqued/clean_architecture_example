package hernanbosqued.backend.presenter.impl

import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.presenter.DTOIdTask
import hernanbosqued.backend.presenter.DTOTask
import hernanbosqued.backend.presenter.Presenter
import hernanbosqued.backend.presenter.Result
import hernanbosqued.backend.presenter.StatusCode
import hernanbosqued.backend.presenter.toDto
import hernanbosqued.backend.service.public.Service

class PresenterImpl(
    private val service: Service,
) : Presenter {
    override fun allTasks(): List<DTOIdTask> = service.allTasks().map { it.toDto() }

    override fun taskById(taskId: Int?): Result<DTOIdTask, StatusCode> {
        if (taskId == null) return Result.Error(StatusCode.BadRequest)

        return when (val task = service.taskById(taskId)) {
            null -> Result.Error(StatusCode.NotFound)
            else -> Result.Success(task.toDto())
        }
    }

    override fun taskByPriority(priorityStr: String?): Result<List<DTOIdTask>, StatusCode> {
        if (priorityStr.isNullOrBlank()) return Result.Error(StatusCode.BadRequest)

        val priority = Priority.entries.find { it.name.equals(priorityStr, ignoreCase = true) }

        return when (priority) {
            null -> Result.Error(StatusCode.BadRequest)
            else -> Result.Success(service.tasksByPriority(priority).map { it.toDto() })
        }
    }

    override fun addTask(task: DTOTask): Result<Unit, StatusCode> {
        service.addTask(task)
        return Result.Success(Unit)
    }

    override fun removeTask(taskId: Int?): Result<Unit, StatusCode> {
        if (taskId == null) return Result.Error(StatusCode.BadRequest)

        return when (service.removeTask(taskId)) {
            false -> Result.Error(StatusCode.NotFound)
            true -> Result.Success(Unit)
        }
    }
}
