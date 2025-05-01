package hernanbosqued.frontend.repository.impl

import hernanbosqued.backend.presenter.DTOIdTask
import hernanbosqued.backend.presenter.DTOTask
import hernanbosqued.backend.presenter.DTOTokenRequest
import hernanbosqued.backend.presenter.DTOUserData
import hernanbosqued.domain.Priority
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.repository.Repository
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

class RepositoryImpl(
    val url: String,
) : Repository {
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

    override suspend fun allTasks(): List<DTOIdTask> = client.get("$url/tasks").body()

    override suspend fun taskById(taskId: Int): DTOIdTask = client.get("$url/tasks/id/$taskId").body()

    override suspend fun taskByPriority(priority: Priority): List<DTOIdTask> = client.get("$url/tasks/priority/$priority").body()

    override suspend fun addTask(task: DTOTask) {
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
        return client.post("$url/code") {
            contentType(ContentType.Application.Json)
            setBody(DTOTokenRequest(clientId, redirectUri, code))
        }.body<DTOUserData>()
    }
}
