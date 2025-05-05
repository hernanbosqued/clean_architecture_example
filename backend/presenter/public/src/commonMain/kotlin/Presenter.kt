package hernanbosqued.backend.presenter

import hernanbosqued.domain.dto.DTOIdTask
import hernanbosqued.domain.dto.DTOTask
import hernanbosqued.domain.dto.DTOTokenRequest
import hernanbosqued.domain.dto.DTOUserData

interface Presenter {
    fun allTasks(): List<DTOIdTask>

    fun taskById(taskId: Long?): Result<DTOIdTask, StatusCode>

    fun taskByPriority(priorityStr: String?): Result<List<DTOIdTask>, StatusCode>

    fun addTask(task: DTOTask): Result<Unit, StatusCode>

    fun removeTask(taskId: Long?): Result<Unit, StatusCode>

    suspend fun getUserData(code: DTOTokenRequest): DTOUserData
}
