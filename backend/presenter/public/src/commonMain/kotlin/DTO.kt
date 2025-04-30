package hernanbosqued.backend.presenter

import hernanbosqued.backend.domain.IdTask
import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.domain.Task
import hernanbosqued.backend.domain.UserData
import kotlinx.serialization.Serializable

@Serializable
data class DTOTask(
    override val name: String,
    override val description: String,
    override val priority: Priority,
) : Task

@Serializable
data class DTOIdTask(
    override val id: Long,
    override val name: String,
    override val description: String,
    override val priority: Priority,
) : IdTask

sealed class StatusCode {
    data object NotFound : StatusCode()

    data object BadRequest : StatusCode()
}

@Serializable
data class TokenResponse(
    @Serializable val access_token: String,
    @Serializable val id_token: String,
    @Serializable val refresh_token: String,
    @Serializable val scope: String,
    val expires_in: Int,
    val token_type: String,
)

@Serializable
data class TokenRequest(
    @Serializable val clientId: String,
    @Serializable val redirectUri: String,
    @Serializable val authorizationCode: String,
)

@Serializable
data class DTOUserData(
    @Serializable override val name: String,
    @Serializable override val email: String,
    @Serializable override val pictureUrl: String,
) : UserData

fun IdTask.toDto(): DTOIdTask = DTOIdTask(id, name, description, priority)

sealed class Result<S, E> {
    data class Success<S, E>(val value: S) : Result<S, E>()

    data class Error<S, E>(val error: E) : Result<S, E>()
}
