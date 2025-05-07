package hernanbosqued.backend.presenter

import hernanbosqued.domain.dto.DTOAuthCodeRequest
import hernanbosqued.domain.dto.DTOAuthRefreshTokenRequest
import hernanbosqued.domain.dto.DTOIdTask
import hernanbosqued.domain.dto.DTOTask
import hernanbosqued.domain.dto.DTOUserData

interface Presenter {
    fun allTasks(userId: String): List<DTOIdTask>

    fun taskByPriority(userId: String, priorityStr: String?): Result<List<DTOIdTask>, StatusCode>

    fun addTask(userId: String, dtoTask: DTOTask): Result<Unit, StatusCode>

    fun removeTask(taskId: Long?): Result<Unit, StatusCode>

    suspend fun getUserData(code: DTOAuthCodeRequest): DTOUserData

    suspend fun refreshToken(code: DTOAuthRefreshTokenRequest): DTOUserData
}
