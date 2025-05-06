package hernanbosqued.frontend.repository.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task
import hernanbosqued.domain.UserData
import hernanbosqued.domain.dto.DTOIdTask
import hernanbosqued.domain.dto.DTOAuthRefreshTokenRequest
import hernanbosqued.domain.dto.DTOAuthCodeRequest
import hernanbosqued.domain.dto.DTOAuthRefreshTokenResponse
import hernanbosqued.domain.dto.DTOUserData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

class FrontendRepositoryImpl(
    val url: String,
) : FrontendRepository {
    private val client =
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
        }

    override suspend fun allTasks(): List<IdTask> = client.get("$url/tasks").body()

    override suspend fun taskById(taskId: Int): IdTask = client.get("$url/tasks/id/$taskId").body()

    override suspend fun taskByPriority(priority: Priority): List<IdTask> =
        client.get("$url/tasks/priority/$priority").body()

    override suspend fun addTask(task: Task) {
        client.post("$url/tasks") {
            contentType(ContentType.Application.Json)
            setBody(task)
        }
    }

    override suspend fun removeTask(taskId: Int?) {
        client.delete("$url/tasks/$taskId") {
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun sendAuthorizationCode(
        code: String,
        clientId: String,
        redirectUri: String,
    ): UserData {
        return client.post("$url/auth/code") {
            contentType(ContentType.Application.Json)
            setBody(DTOAuthCodeRequest(clientId, redirectUri, code))
        }.body()
    }

    override suspend fun refreshToken(refreshToken: String): String {
        val response: DTOAuthRefreshTokenResponse = client.post("$url/auth/refreshToken") {
            contentType(ContentType.Application.Json)
            setBody(DTOAuthRefreshTokenRequest(refreshToken))
        }.body()

        return response.accessToken
    }
}
