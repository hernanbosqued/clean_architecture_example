package hernanbosqued.frontend.platform_controller

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.UserData
import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class LoginActionsBase(
    val clientId: String,
    val redirectUri: String,
    val scopes: List<String>,
    val repository: FrontendRepository,
) : LoginActions {
    override val userData: MutableSharedFlow<UserData?> = MutableSharedFlow(replay = 1)

    suspend fun sendAuthCode(authCode: String): UserData {
        return repository.sendAuthorizationCode(authCode, clientId, redirectUri)
    }

    fun generateAuthorizationUrl(
        clientId: String,
        redirectUri: String,
        scopes: List<String>,
    ): String {
        val parameters =
            Parameters.build {
                append("client_id", clientId)
                append("redirect_uri", redirectUri)
                append("prompt", "consent")
                append("access_type", "offline")
                append("scope", scopes.joinToString(" "))
                append("response_type", "code")
            }

        val urlBuilder = URLBuilder("https://accounts.google.com/o/oauth2/v2/auth")
        urlBuilder.parameters.appendAll(parameters)
        return urlBuilder.buildString()
    }
}
