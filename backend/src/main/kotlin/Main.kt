package hernanbosqued.backend

import hernanbosqued.backend.controller.di.ControllerModule
import hernanbosqued.backend.presenter.DTOTask
import hernanbosqued.backend.presenter.Presenter
import hernanbosqued.backend.presenter.Result
import hernanbosqued.backend.presenter.StatusCode
import hernanbosqued.backend.presenter.di.PresenterModule
import hernanbosqued.backend.service.di.ServiceModule
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticResources
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun getModules(dbPath: String) =
    listOf(
        ControllerModule.getModule(dbPath),
        PresenterModule.getModule(),
        ServiceModule.getModule(),
    )

fun main(args: Array<String>) {
    embeddedServer(
        Netty,
        port = 8081,
        module = { main(args[0]) }
    ).start(wait = true)
}

fun Application.main(path: String) {
    install(Koin) {
        modules(getModules(path))
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
                val taskId = call.parameters["taskId"]?.toLongOrNull()

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
                val taskId = call.parameters["taskId"]?.toLongOrNull()
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
