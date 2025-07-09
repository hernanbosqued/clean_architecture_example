package hernanbosqued.backend.presenter.impl

import hernanbosqued.backend.presenter.Presenter
import hernanbosqued.backend.presenter.Result
import hernanbosqued.backend.presenter.StatusCode
import hernanbosqued.backend.presenter.toDto
import hernanbosqued.backend.use_case.auth.AuthUseCase
import hernanbosqued.backend.use_case.db.DbUseCase
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task
import hernanbosqued.domain.dto.DTOAuthCodeRequest
import hernanbosqued.domain.dto.DTOAuthRefreshTokenRequest
import hernanbosqued.domain.dto.DTOIdTask
import hernanbosqued.domain.dto.DTOTask
import hernanbosqued.domain.dto.DTOUserData

class PresenterImpl(
    private val dbUseCase: DbUseCase,
    private val authUseCase: AuthUseCase,
) : Presenter {
    override fun allTasks(userId: String): List<DTOIdTask> {
        return dbUseCase.allTasks(userId).map { it.toDto() }
    }

    override fun taskByPriority(userId: String, priorityStr: String?): Result<List<DTOIdTask>, StatusCode> {
        if (priorityStr.isNullOrBlank()) return Result.Error(StatusCode.BadRequest)

        val priority = Priority.entries.find { it.name.equals(priorityStr, ignoreCase = true) }

        return when (priority) {
            null -> Result.Error(StatusCode.BadRequest)
            else -> Result.Success(dbUseCase.tasksByPriority(userId, priority).map { it.toDto() })
        }
    }

    override fun addTask(userId: String, dtoTask: DTOTask): Result<Unit, StatusCode> {
        dbUseCase.addTask(userId, dtoTask)
        return Result.Success(Unit)
    }

    override fun removeTask(taskId: Long?): Result<Unit, StatusCode> {
        if (taskId == null) return Result.Error(StatusCode.BadRequest)

        return when (dbUseCase.removeTask(taskId)) {
            false -> Result.Error(StatusCode.NotFound)
            true -> Result.Success(Unit)
        }
    }

    override suspend fun getUserData(code: DTOAuthCodeRequest): DTOUserData {
        return authUseCase.getUserData(code).toDto()
    }

    override suspend fun refreshToken(code: DTOAuthRefreshTokenRequest): DTOUserData {
        return authUseCase.refreshToken(code).toDto()
    }

    override fun verifyTotp(userId: String, totp: Int): Boolean {
        return dbUseCase.getMfaSecret(userId)?.let { mfaSecret ->
            authUseCase.verifyTotp(mfaSecret, totp)
        } ?: false
    }
}
