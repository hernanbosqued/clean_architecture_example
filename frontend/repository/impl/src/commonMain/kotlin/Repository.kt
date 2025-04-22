package hernanbosqued.frontend.repository

import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.presenter.DTOIdTask
import hernanbosqued.backend.presenter.DTOTask
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

class Repository {
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

    suspend fun allTasks(): List<DTOIdTask> = client.get("http://localhost:8081/tasks").body()

    suspend fun taskById(taskId: Int): DTOIdTask = client.get("http://localhost:8081/tasks/id/$taskId").body()

    suspend fun taskByPriority(priority: Priority): List<DTOIdTask> = client.get("http://localhost:8081/tasks/priority/$priority").body()

    suspend fun addTask(task: DTOTask) {
        client.post("http://localhost:8081/tasks") {
            contentType(ContentType.Application.Json)
            setBody(task)
        }
    }

    suspend fun removeTask(taskId: Int?) {
        client.delete("http://localhost:8081/tasks/$taskId") {
            contentType(ContentType.Application.Json)
        }
    }
}
