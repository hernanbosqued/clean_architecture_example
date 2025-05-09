package hernanbosqued.frontend.platform_controller.impl

import hernanbosqued.domain.AuthData
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.authProviders
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json

object HttpClientFactory {
    fun create(
        tokensProviderFunc: () -> AuthData?,
        refreshTokenFunc: suspend () -> AuthData?
    ): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(Auth) {
            bearer {
                loadTokens {
                    tokensProviderFunc()?.let {
                        BearerTokens(it.idToken, it.refreshToken)
                    }
                }

                refreshTokens {
                    refreshTokenFunc()?.let {
                        BearerTokens(it.idToken, it.refreshToken)
                    }
                }
            }
        }
    }
}

fun HttpClient.invalidateToken() = authProviders.filterIsInstance<BearerAuthProvider>().firstOrNull()?.clearToken() ?: Unit
