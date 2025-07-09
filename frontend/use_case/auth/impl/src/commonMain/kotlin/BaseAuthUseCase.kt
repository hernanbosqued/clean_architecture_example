package hernanbosqued.frontend.use_case.auth.impl

import hernanbosqued.domain.AuthData
import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.Persistence
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import kotlinx.coroutines.flow.StateFlow

abstract class BaseAuthUseCase(
    val clientId: String,
    val redirectUri: String,
    val scopes: List<String>,
    val frontendRepository: FrontendRepository,
    val persistence: Persistence,
    val tokenInvalidator: () -> Unit
) : AuthUseCase {

    override val userData: StateFlow<UserData?> = persistence.userData

    override suspend fun logout() {
        persistence.clearUserData()
    }

    override suspend fun getUserDataFromAuthCode(authCode: String) {
        tokenInvalidator.invoke()

        frontendRepository.getUserData(authCode, clientId, redirectUri).also {
            persistence.saveUserData(it)
        }
    }

    fun generateAuthorizationUrl(): String {
        val parameters = Parameters.build {
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

    override fun provideTokens(): AuthData? {
        return userData.value
    }

    override suspend fun refreshTokens(): AuthData? {
        val currentRefreshToken = userData.value?.refreshToken
        return if (currentRefreshToken != null) {
            try {
                frontendRepository.refreshToken(currentRefreshToken).also {
                    persistence.saveUserData(it)
                }
            } catch (_: Exception) {
                persistence.clearUserData()
                null
            }
        } else {
            persistence.clearUserData()
            null
        }
    }

    override suspend fun sendTotp(totp: Int): Boolean = frontendRepository.sendTotp(totp).also { isMfaAuthenticated ->
        userData.value?.let { userData ->
            persistence.saveUserData(object : UserData {
                override val name = userData.name
                override val email = userData.email
                override val pictureUrl = userData.pictureUrl
                override val qrCode = userData.qrCode
                override val isMfaAuthenticated = isMfaAuthenticated
                override val idToken = userData.idToken
                override val refreshToken = userData.refreshToken
            })
        }
    }
}