package hernanbosqued.frontend.repository.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Persistence
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task
import hernanbosqued.domain.UserData
import hernanbosqued.domain.dto.DTOAuthCodeRequest
import hernanbosqued.domain.dto.DTOAuthRefreshTokenRequest
import hernanbosqued.domain.dto.DTOIdTask
import hernanbosqued.domain.dto.DTOUserData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

class FrontendRepositoryImpl(
    val url: String,
    val authPersistence: Persistence,
) : FrontendRepository {
    private val client =
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        // Carga los tokens desde tu persistencia
                        val userData = authPersistence.loadUserData()
                        if (userData != null && userData.accessToken.isNotBlank()) {
                            BearerTokens(userData.accessToken, userData.refreshToken ?: "")
                        } else {
                            null // No hay tokens o el access token está vacío
                        }
                    }

                    refreshTokens {
                        // Lógica para refrescar el token si loadTokens devuelve null o si una petición falla con 401
                        // Este bloque se ejecutará si el servidor devuelve 401 Unauthorized.
                        val currentData = authPersistence.loadUserData()
                        val currentRefreshToken = currentData?.refreshToken

                        if (currentRefreshToken != null) {
                            try {
                                // Llama a tu lógica de refresh token del repositorio
                                // ¡IMPORTANTE! Esta llamada a frontendRepository.refreshToken
                                // NO debe ser interceptada por este mismo Auth bearer provider.
                                // sendWithoutRequest se encarga de esto.
                                val newUserData = refreshToken(currentRefreshToken)
                                authPersistence.saveUserData(newUserData) // Guarda los nuevos tokens
                                BearerTokens(newUserData.accessToken, newUserData.refreshToken ?: "")
                            } catch (e: Exception) {
                                // Si el refresh falla (ej. refresh token inválido),
                                // limpia los tokens y devuelve null para indicar fallo.
                                authPersistence.clearUserData()
                                // Podrías emitir un evento para que la UI reaccione (ej. redirigir a login)
                                // authUseCase.userData.emit(null) // O similar, cuidado con el contexto de corrutina
                                println("Error refreshing token: ${e.message}")
                                null
                            }
                        } else {
                            // No hay refresh token, no se puede refrescar
                            authPersistence.clearUserData() // Limpia por si acaso
                            null
                        }
                    }

                    // Configura para qué peticiones NO se debe enviar el token.
                    // Es crucial para evitar enviar el token a los endpoints de login/refresh.
//                    sendWithoutRequest { request ->
//                        // Compara con las rutas de tus endpoints de autenticación
//                        val path = request.url.encodedPath
//         //               path.contains("/tasks") || // Endpoint para iniciar sesión
//                        path.endsWith("/auth/code") || // Endpoint para enviar authCode
//                        path.endsWith("/auth/refresh_token") // Endpoint para refrescar token
//                        // Añade aquí cualquier otra ruta pública que no requiera autenticación
//                    }
                }
            }
        }

    override suspend fun allTasks(): List<IdTask> = client.get("$url/tasks").body<List<DTOIdTask>>()

    override suspend fun taskById(taskId: Int): IdTask = client.get("$url/tasks/id/$taskId").body()

    override suspend fun taskByPriority(priority: Priority): List<IdTask> = client.get("$url/tasks/priority/$priority").body()

    override suspend fun addTask(task: Task) {
        client.post("$url/tasks") {
            contentType(ContentType.Application.Json)
            setBody(task)
        }
    }

    override suspend fun removeTask(taskId: Int?) {
        client.delete("$url/tasks/$taskId") {
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun sendAuthorizationCode(
        code: String,
        clientId: String,
        redirectUri: String,
    ): UserData {
        return client.post("$url/auth/code") {
            contentType(ContentType.Application.Json)
            setBody(DTOAuthCodeRequest(clientId, redirectUri, code))
        }.body<DTOUserData>()
    }

    override suspend fun refreshToken(refreshToken: String): UserData {
        return client.post("$url/auth/refresh_token") {
            contentType(ContentType.Application.Json)
            setBody(DTOAuthRefreshTokenRequest(refreshToken))
        }.body<DTOUserData>()
    }
}
