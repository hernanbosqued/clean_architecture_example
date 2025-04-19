package hernanbosqued.backend

import hernanbosqued.backend.controller.di.ControllerModule
import hernanbosqued.backend.presenter.DTOTask
import hernanbosqued.backend.presenter.Presenter
import hernanbosqued.backend.presenter.Result
import hernanbosqued.backend.presenter.StatusCode
import hernanbosqued.backend.presenter.di.PresenterModule
import hernanbosqued.backend.service.di.ServiceModule
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import java.nio.file.Paths

fun getModules() = listOf(
    ControllerModule.getModule(
        path = Paths.get("").toAbsolutePath().toString() + "/backend/controller/impl/src/main/resources/db.json"
    ),
    PresenterModule.getModule(),
    ServiceModule.getModule()
)

fun main() {
    embeddedServer(
        Netty, port = 8081, // This is the port on which Ktor is listening
        host = "0.0.0.0", module = Application::main
    ).start(wait = true)
}

fun Application.main() {
    install(Koin) {
        modules(getModules())
    }

    install(ContentNegotiation) {
        json()
    }

    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Delete)
    }

    val presenter: Presenter by inject()

    routing {
        staticResources("static", "static")

        route("/tasks") {
            get {
                call.respond(presenter.allTasks())
            }

            get("/id/{taskId}") {
                val taskId = call.parameters["taskId"]?.toIntOrNull()

                when (val result = presenter.taskById(taskId)) {
                    is Result.Success -> call.respond(result.value)
                    is Result.Error -> call.respond(result.error.map())
                }
            }

            get("/priority/{priority}") {
                val priorityAsText = call.parameters["priority"]?.lowercase()

                when (val result = presenter.taskByPriority(priorityAsText)) {
                    is Result.Success -> call.respond(result.value)
                    is Result.Error -> call.respond(result.error.map())
                }
            }

            post {
                val task = call.receive<DTOTask>()
                presenter.addTask(task)
                call.respond(HttpStatusCode.Created)
            }

            delete("/{taskId}") {
                val taskId = call.parameters["taskId"]?.toIntOrNull()
                when (val result = presenter.removeTask(taskId)) {
                    is Result.Success -> call.respond(HttpStatusCode.Accepted)
                    is Result.Error -> call.respond(result.error.map())
                }
            }
        }
    }
}

private fun StatusCode.map(): HttpStatusCode {
    return when (this) {
        StatusCode.BadRequest -> HttpStatusCode.BadRequest
        StatusCode.NotFound -> HttpStatusCode.NotFound
    }
}