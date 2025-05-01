package hernanbosqued.backend.presenter

import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task
import hernanbosqued.domain.TokenRequest
import hernanbosqued.domain.UserData
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

@Serializable
data class DTOTokenRequest(
    override val clientId: String,
    override val redirectUri: String,
    override val authorizationCode: String,
) : TokenRequest

@Serializable
data class DTOUserData(
    override val name: String,
    override val email: String,
    override val pictureUrl: String,
) : UserData
