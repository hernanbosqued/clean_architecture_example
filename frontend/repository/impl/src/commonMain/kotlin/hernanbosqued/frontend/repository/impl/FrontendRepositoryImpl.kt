package hernanbosqued.frontend.repository.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task
import hernanbosqued.domain.UserData
import hernanbosqued.domain.dto.DTOAuthCodeRequest
import hernanbosqued.domain.dto.DTOAuthRefreshTokenRequest
import hernanbosqued.domain.dto.DTOIdTask
import hernanbosqued.domain.dto.DTOTask
import hernanbosqued.domain.dto.DTOUserData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class FrontendRepositoryImpl(
    val url: String,
    val client: HttpClient
) : FrontendRepository {

    override suspend fun allTasks(): List<IdTask> = client.get("$url/tasks").body<List<DTOIdTask>>()

    override suspend fun taskByPriority(priority: Priority): List<IdTask> = client.get("$url/tasks/priority/$priority").body<List<DTOIdTask>>()

    override suspend fun addTask(task: Task) = client.post("$url/tasks") {
        contentType(ContentType.Application.Json)
        setBody(DTOTask(task.name, task.description, task.priority))
    }.body<Unit>()


    override suspend fun removeTask(taskId: Long) = client.delete("$url/tasks/$taskId") {
        contentType(ContentType.Application.Json)
    }.body<Unit>()


    override suspend fun getUserData(authorizationCode: String, clientId: String, redirectUri: String): UserData = client.post("$url/auth/code") {
        contentType(ContentType.Application.Json)
        setBody(DTOAuthCodeRequest(clientId, redirectUri, authorizationCode))
    }.body<DTOUserData>()


    override suspend fun refreshToken(refreshToken: String): UserData = client.post("$url/auth/refresh_token") {
        contentType(ContentType.Application.Json)
        setBody(DTOAuthRefreshTokenRequest(refreshToken))
    }.body<DTOUserData>()
}