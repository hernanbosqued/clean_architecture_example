package hernanbosqued.frontend.ui

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import hernanbosqued.frontend.buildconfig.BuildKonfig
import hernanbosqued.frontend.repository.di.RepositoryModule
import hernanbosqued.frontend.ui.viewModels.AuthViewModel
import hernanbosqued.frontend.usecase.auth.di.DesktopAuthUseCaseModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

// import com.auth0.jwt.JWT
// import com.auth0.jwt.interfaces.DecodedJWT
// import io.ktor.client.*
// import io.ktor.client.request.*
// import io.ktor.client.request.forms.*
// import io.ktor.client.statement.*
// import io.ktor.http.*
// import kotlinx.coroutines.Dispatchers
// import kotlinx.coroutines.launch
// import kotlinx.coroutines.withContext
// import kotlinx.serialization.ExperimentalSerializationApi
// import kotlinx.serialization.KSerializer
// import kotlinx.serialization.Serializable
// import kotlinx.serialization.encoding.Decoder
// import kotlinx.serialization.encoding.Encoder
// import kotlinx.serialization.json.Json
// import kotlinx.serialization.serializer
// import java.awt.Desktop
// import java.net.URI
// import java.net.URLEncoder
// import java.nio.charset.StandardCharsets

fun main() =
    application {
        startKoin {
            modules(
                RepositoryModule.getModule(BuildKonfig.apiUrl),
                DesktopAuthUseCaseModule.getModule(),
                module {
                    single {
                        AuthViewModel(
                            authUseCase =
                                get {
                                    parametersOf(
                                        BuildKonfig.clientId,
                                        BuildKonfig.redirectUri,
                                        listOf("profile", "email"),
                                    )
                                },
                            coroutineScope = CoroutineScope(Dispatchers.Default),
                        )
                    }
                },
            )
        }

        Window(
            onCloseRequest = ::exitApplication,
            title = "Clean Architecture Example",
        ) {
//            val authCodeReceiver = AuthCodeReceiver()
//            val server = authCodeReceiver.startServer(8082)
//            val authorizationUrl = generateAuthorizationUrl(clientId, redirectUri, scopes)
//            openWebpage(URI(authorizationUrl))
//            val scope = rememberCoroutineScope()
//
//            scope.launch {
//                val authorizationCode = authCodeReceiver.waitForCode()
//                server.stop(gracePeriodMillis = 100, timeoutMillis = 500)
//                if (authorizationCode != null) {
//                    println("Código de autorización recibido: $authorizationCode")
//                    val tokens = exchangeCodeForTokens(
//                        clientId = clientId,
//                        clientSecret = clientSecret,
//                        redirectUri = redirectUri,
//                        authorizationCode = authorizationCode
//                    )
//
//                val refreshTokenResponse = refreshToken(
//                    clientId = clientId,
//                    clientSecret = clientSecret,
//                    refreshToken = tokens?.refresh_token!!
//                    refreshToken = refreshToken
//                )
//
//                extractUserDataFromIdToken(refreshTokenResponse)
//               } else {
//                    println("No se recibió el código de autorización o la autenticación fue cancelada.")
//                    // Manejar el error o la cancelación
//                }
//            }
            App()
        }
    }

// fun extractUserDataFromIdToken(tokenResponse: TokenRefreshResponse?): Map<String, String>? {
//    return try {
//        val jwt: DecodedJWT = JWT.decode(tokenResponse?.id_token)
//        val claims = jwt.claims
//
//        val name = claims["name"]?.asString()
//        val email = claims["email"]?.asString()
//        val picture = claims["picture"]?.asString()
//
//        mapOf(
//            "name" to (name ?: ""),
//            "email" to (email ?: ""),
//            "picture" to (picture ?: "")
//        )
//    } catch (e: Exception) {
//        println("Error al decodificar el ID token: ${e.message}")
//        null
//    }
// }

// @Serializable
// data class TokenResponse(
//    @Serializable(with = NullableStringSerializer::class) val access_token: String? = null,
//    @Serializable(with = NullableStringSerializer::class) val id_token: String? = null,
//    @Serializable(with = NullableStringSerializer::class) val refresh_token: String? = null,
//    @Serializable(with = NullableStringSerializer::class) val scope: String? = null,
//    val expires_in: Int? = null,
//    val token_type: String? = null
// )
//
// // Serializer para permitir valores nulos en las propiedades String de TokenResponse
// @OptIn(ExperimentalSerializationApi::class)
// object NullableStringSerializer : KSerializer<String?> {
//    override val descriptor = serializer<String?>().descriptor
//    override fun serialize(encoder: Encoder, value: String?) = encoder.encodeNullableSerializableValue(serializer<String>(), value)
//    override fun deserialize(decoder: Decoder): String? = decoder.decodeNullableSerializableValue(serializer<String>())
// }
//
// suspend fun exchangeCodeForTokens(
//    clientId: String,
//    clientSecret: String,
//    redirectUri: String,
//    authorizationCode: String
// ): TokenResponse? = withContext(Dispatchers.IO) {
//    val client = HttpClient()
//    return@withContext try {
//        val response = client.post("https://oauth2.googleapis.com/token") {
//            setBody(FormDataContent(Parameters.build {
//                append("code", authorizationCode)
//                append("client_id", clientId)
//                append("client_secret", clientSecret)
//                append("redirect_uri", redirectUri)
//                append("grant_type", "authorization_code")
//            }))
//            contentType(ContentType.Application.FormUrlEncoded)
//        }
//        if (response.status.isSuccess()) {
//            val json = Json.decodeFromString<TokenResponse>(response.bodyAsText())
//            json
//        } else {
//            println("Error al intercambiar el código por tokens: ${response.status} - ${response.bodyAsText()}")
//            null
//        }
//    } catch (e: Exception) {
//        println("Excepción al intercambiar el código por tokens: ${e.message}")
//        null
//    } finally {
//        client.close()
//    }
// }
//
// @Serializable
// data class TokenRefreshResponse(
//    val access_token: String? = null,
//    val id_token: String? = null,
//    val expires_in: Int? = null,
//    val token_type: String? = null,
//    val scope: String? = null
// )
//
// suspend fun refreshToken(
//    clientId: String,
//    clientSecret: String,
//    refreshToken: String
// ): TokenRefreshResponse? = withContext(Dispatchers.IO) {
//    val client = HttpClient()
//    return@withContext try {
//        val response = client.post("https://oauth2.googleapis.com/token") {
//            setBody(FormDataContent(Parameters.build {
//                append("grant_type", "refresh_token")
//                append("client_id", clientId)
//                append("client_secret", clientSecret)
//                append("refresh_token", refreshToken)
//            }))
//            contentType(ContentType.Application.FormUrlEncoded)
//        }
//        if (response.status.isSuccess()) {
//            Json.decodeFromString<TokenRefreshResponse>(response.bodyAsText())
//        } else {
//            println("Error al renovar el token: ${response.status} - ${response.bodyAsText()}")
//            null
//        }
//    } catch (e: Exception) {
//        println("Excepción al renovar el token: ${e.message}")
//        null
//    } finally {
//        client.close()
//    }
// }
