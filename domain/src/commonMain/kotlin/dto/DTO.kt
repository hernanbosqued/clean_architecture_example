package hernanbosqued.domain.dto

import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task
import hernanbosqued.domain.AuthCodeRequest
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
data class DTOAuthCodeRequest(
    override val clientId: String,
    override val redirectUri: String,
    override val authorizationCode: String,
) : AuthCodeRequest

@Serializable
data class DTOAuthRefreshTokenRequest(
    val refreshToken: String,
)

@Serializable
data class DTOAuthRefreshTokenResponse(
    val accessToken: String,
)

@Serializable
data class DTOUserData(
    override val name: String,
    override val email: String,
    override val pictureUrl: String,
    override val accessToken: String,
    override val refreshToken: String
) : UserData
