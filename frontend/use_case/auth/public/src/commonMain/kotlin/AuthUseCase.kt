package hernanbosqued.frontend.usecase.auth

import io.ktor.http.Parameters
import io.ktor.http.URLBuilder

interface AuthUseCase {
    val clientId: String
    val redirectUri: String
    val scopes: List<String>

    suspend fun step1(): String?

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
                append("scope", scopes.joinToString(" ")) // Scopes are space-separated
                append("response_type", "code")
            }

        val urlBuilder = URLBuilder("https://accounts.google.com/o/oauth2/v2/auth")
        urlBuilder.parameters.appendAll(parameters)
        return urlBuilder.buildString()
    }
}
