package hernanbosqued.frontend.use_case.auth.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.Persistence
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseAuthUseCase(
    val clientId: String,
    val redirectUri: String,
    val scopes: List<String>,
    val frontendRepository: FrontendRepository,
    val authPersistence: Persistence,
) : AuthUseCase {
    override val userData: MutableSharedFlow<UserData?> = MutableSharedFlow(replay = 1)

    override suspend fun init() {
        authPersistence.loadUserData()?.let {
            val refreshToken = it.refreshToken

            if (refreshToken != null) {
                val userData = frontendRepository.refreshToken(refreshToken)
                authPersistence.saveUserData(userData)
                this.userData.emit(authPersistence.loadUserData())
            } else {
                login()
            }
        }
    }

    override suspend fun logout() {
        authPersistence.clearUserData()
        userData.emit(null)
    }

    override suspend fun getUserDataFromAuthCode(authCode: String) {
        val userData = frontendRepository.sendAuthorizationCode(authCode, clientId, redirectUri)
        authPersistence.saveUserData(userData)
        this.userData.emit(userData)
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
