package hernanbosqued.backend

import hernanbosqued.backend.presenter.Presenter
import hernanbosqued.backend.presenter.Result
import hernanbosqued.domain.dto.DTOTask
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Routing.addTaskRouting(presenter: Presenter) = authenticate("auth-google") {
    route("/tasks") {
        get {
            call.withUserId { userId ->
                call.respond(presenter.allTasks(userId))
            }
        }

        get("/priority/{priority}"){
            call.withUserId { userId ->
                val priorityAsText = call.parameters["priority"]?.lowercase()

                when (val result = presenter.taskByPriority(userId, priorityAsText)) {
                    is Result.Success -> call.respond(result.value)
                    is Result.Error -> call.respond(result.error.map())
                }
            }
        }

        post {
            call.withUserId { userId ->
                val payload = call.receive<DTOTask>()
                presenter.addTask(userId, payload)
                call.respond(HttpStatusCode.Created)
            }
        }

        delete("/{taskId}") {
            val taskId = call.parameters["taskId"]?.toLongOrNull()
            when (val result = presenter.removeTask(taskId)) {
                is Result.Success -> call.respond(HttpStatusCode.Accepted)
                is Result.Error -> call.respond(result.error.map())
            }
        }
    }
}