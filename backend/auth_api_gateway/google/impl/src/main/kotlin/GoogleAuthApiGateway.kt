package hernanbosqued.backend.auth_api_gateway_google.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import hernanbosqued.config.Constants
import hernanbosqued.domain.AuthApiGateway
import hernanbosqued.domain.AuthCodeRequest
import hernanbosqued.domain.UserData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType

class GoogleAuthApiGateway(
    val httpClient: HttpClient,
) : AuthApiGateway {
    override suspend fun getUserData(code: AuthCodeRequest): UserData {
        val tokenResponse = getGoogleTokens(
            clientId = code.clientId,
            clientSecret = Constants.GOOGLE_SECRET,
            redirectUri = code.redirectUri,
            authorizationCode = code.authorizationCode,
        )

        return extractUserDataFromIdToken(tokenResponse)
    }

    suspend fun getGoogleTokens(
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        authorizationCode: String,
    ): DTOGoogleTokens {
        val response =
            httpClient.post("https://oauth2.googleapis.com/token") {
                setBody(
                    FormDataContent(
                        Parameters.build {
                            append("code", authorizationCode)
                            append("client_id", clientId)
                            append("client_secret", clientSecret)
                            append("redirect_uri", redirectUri)
                            append("grant_type", "authorization_code")
                        },
                    ),
                )
                contentType(ContentType.Application.FormUrlEncoded)
            }

        return response.body()
    }

    fun extractUserDataFromIdToken(tokenResponse: DTOGoogleTokens): UserData {
        val jwt: DecodedJWT = JWT.decode(tokenResponse.idToken)
        return object : UserData {
            override val name: String = requireNotNull(jwt.claims["name"]).asString()
            override val email: String = requireNotNull(jwt.claims["email"]).asString()
            override val pictureUrl: String = requireNotNull(jwt.claims["picture"]).asString()
            override val accessToken: String = tokenResponse.accessToken
            override val refreshToken: String = tokenResponse.refreshToken
        }
    }
}
