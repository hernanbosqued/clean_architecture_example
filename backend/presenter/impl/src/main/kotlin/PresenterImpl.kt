package hernanbosqued.backend.presenter.impl

import hernanbosqued.domain.dto.DTOIdTask
import hernanbosqued.domain.dto.DTOTask
import hernanbosqued.domain.dto.DTOTokenRequest
import hernanbosqued.domain.dto.DTOUserData
import hernanbosqued.backend.presenter.Presenter
import hernanbosqued.backend.presenter.Result
import hernanbosqued.backend.presenter.StatusCode
import hernanbosqued.backend.presenter.toDto
import hernanbosqued.backend.use_case.auth.AuthUseCase
import hernanbosqued.backend.use_case.db.DbUseCase
import hernanbosqued.domain.Priority

class PresenterImpl(
    private val dbUseCase: DbUseCase,
    private val authUseCase: AuthUseCase,
) : Presenter {
    override fun allTasks(): List<DTOIdTask> {
        return dbUseCase.allTasks().map { it.toDto() }
    }

    override fun taskById(taskId: Long?): Result<DTOIdTask, StatusCode> {
        if (taskId == null) return Result.Error(StatusCode.BadRequest)

        return when (val task = dbUseCase.taskById(taskId)) {
            null -> Result.Error(StatusCode.NotFound)
            else -> Result.Success(task.toDto())
        }
    }

    override fun taskByPriority(priorityStr: String?): Result<List<DTOIdTask>, StatusCode> {
        if (priorityStr.isNullOrBlank()) return Result.Error(StatusCode.BadRequest)

        val priority = Priority.entries.find { it.name.equals(priorityStr, ignoreCase = true) }

        return when (priority) {
            null -> Result.Error(StatusCode.BadRequest)
            else -> Result.Success(dbUseCase.tasksByPriority(priority).map { it.toDto() })
        }
    }

    override fun addTask(task: DTOTask): Result<Unit, StatusCode> {
        dbUseCase.addTask(task)
        return Result.Success(Unit)
    }

    override fun removeTask(taskId: Long?): Result<Unit, StatusCode> {
        if (taskId == null) return Result.Error(StatusCode.BadRequest)

        return when (dbUseCase.removeTask(taskId)) {
            false -> Result.Error(StatusCode.NotFound)
            true -> Result.Success(Unit)
        }
    }

    override suspend fun getUserData(code: DTOTokenRequest): DTOUserData {
        return authUseCase.getUserData(code).toDto()
    }
}
