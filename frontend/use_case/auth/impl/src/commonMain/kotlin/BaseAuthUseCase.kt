package hernanbosqued.frontend.use_case.auth.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import kotlinx.coroutines.flow.StateFlow

abstract class BaseAuthUseCase(
    val clientId: String,
    val redirectUri: String,
    val scopes: List<String>,
    val frontendRepository: FrontendRepository
) : AuthUseCase {

    override val userData: StateFlow<UserData?> = frontendRepository.userData

    override suspend fun logout() {
        frontendRepository.logout()
    }

    override suspend fun getUserDataFromAuthCode(authCode: String) {
       frontendRepository.sendAuthorizationCode(authCode, clientId, redirectUri)
    }

    fun generateAuthorizationUrl(): String {
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
