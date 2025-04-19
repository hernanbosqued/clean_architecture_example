package hernanbosqued.frontend.repository

import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.presenter.DTOIdTask
import hernanbosqued.backend.presenter.DTOTask
import hernanbosqued.backend.presenter.Result
import hernanbosqued.backend.presenter.StatusCode
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*


class Repository {
    private val client = HttpClient {
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

