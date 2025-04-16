package hernanbosqued.backend

import hernanbosqued.backend.domain.Repository
import hernanbosqued.backend.presenter.DTOTask
import hernanbosqued.backend.presenter.Presenter
import hernanbosqued.backend.presenter.StatusCode
import hernanbosqued.backend.presenter.Result
import hernanbosqued.backend.repo.DatabaseRepository
import hernanbosqued.backend.service.public.Service
import hernanbosqued.backend.use_cases.ServiceImpl
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun getModule() = module {
    factory<Repository> {
        DatabaseRepository()
    }

    single<Service> {
        ServiceImpl(
            repository = get()
        )
    }
    single<Presenter> {
        Presenter(
            service = get()
        )
    }
}

fun main() {
    embeddedServer(
        Netty,
        port = 8080, // This is the port on which Ktor is listening
        host = "0.0.0.0",
        module = Application::main
    ).start(wait = true)
}

fun Application.main() {
    install(Koin) {
        modules(getModule())
    }

    install(ContentNegotiation) {
        json()
    }

    val presenter by inject<Presenter>()

    routing {
        staticResources("static", "static")

        route("/tasks") {
            get {
                call.respond(presenter.allTasks())
            }

            get("/id/{taskId}") {
                val taskId = call.parameters["taskId"]?.toIntOrNull()

                when(val result = presenter.taskById(taskId)){
                    is Result.Success -> call.respond(result.value)
                    is Result.Error -> call.respond(result.error.map())
                }
            }

            get("/priority/{priority}") {
                val priorityAsText = call.parameters["priority"]?.lowercase()

                when(val result = presenter.taskByPriority(priorityAsText)){
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
                when(val result = presenter.removeTask(taskId)){
                    is Result.Success -> call.respond(HttpStatusCode.Accepted)
                    is Result.Error -> call.respond(result.error.map())
                }
            }
        }
    }
}

private fun StatusCode.map(): HttpStatusCode{
    return when(this){
        StatusCode.BadRequest -> HttpStatusCode.BadRequest
        StatusCode.NotFound -> HttpStatusCode.NotFound
    }
}

