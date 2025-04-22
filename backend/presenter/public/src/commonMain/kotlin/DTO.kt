package hernanbosqued.backend.presenter

import hernanbosqued.backend.domain.IdTask
import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.domain.Task
import kotlinx.serialization.Serializable

@Serializable
data class DTOTask(
    override val name: String,
    override val description: String,
    override val priority: Priority,
) : Task

@Serializable
data class DTOIdTask(
    override val id: Int,
    override val name: String,
    override val description: String,
    override val priority: Priority,
) : IdTask

sealed class StatusCode {
    data object NotFound : StatusCode()

    data object BadRequest : StatusCode()
}

fun IdTask.toDto(): DTOIdTask = DTOIdTask(id, name, description, priority)

sealed class Result<S, E> {
    data class Success<S, E>(val value: S) : Result<S, E>()

    data class Error<S, E>(val error: E) : Result<S, E>()
}
