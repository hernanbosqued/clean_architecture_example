package hernanbosqued.backend.auth_api_gateway_google.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import hernanbosqued.config.Constants
import hernanbosqued.domain.AuthApiGateway
import hernanbosqued.domain.AuthCodeRequest
import hernanbosqued.domain.AuthRefreshTokenRequest
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
            grantType = "authorization_code",
            clientId = code.clientId, //PASAR A CONST EN EL BACKEND
            clientSecret = Constants.GOOGLE_SECRET,
            redirectUri = code.redirectUri,
            authorizationCode = code.authorizationCode,
        )

        return extractUserDataFromIdToken(tokenResponse)
    }

    override suspend fun refreshToken(code: AuthRefreshTokenRequest): UserData {
        val tokenResponse = getGoogleTokens(
            grantType = "refresh_token",
            clientId = "842809767945-3b6q9v34d40rct3q2rfl3goq8isd5f3p.apps.googleusercontent.com", //PASAR A CONST EN EL BACKEND
            clientSecret = Constants.GOOGLE_SECRET,
            refreshToken = code.refreshToken
        )
        return extractUserDataFromIdToken(tokenResponse)
    }

    suspend fun getGoogleTokens(
        grantType: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String? = null,
        authorizationCode: String? = null,
        refreshToken: String? = null,
    ): DTOGoogleTokens {
        val response =
            httpClient.post("https://oauth2.googleapis.com/token") {
                setBody(
                    FormDataContent(
                        Parameters.build {
                            append("grant_type", grantType)
                            append("client_id", clientId)
                            append("client_secret", clientSecret)
                            authorizationCode?.let { append("code", authorizationCode) }
                            redirectUri?.let { append("redirect_uri", redirectUri) }
                            refreshToken?.let { append("refresh_token", refreshToken) }
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
            override val name: String? = jwt.claims["name"]?.asString()
            override val email: String = requireNotNull(jwt.claims["email"]).asString()
            override val pictureUrl: String? = jwt.claims["picture"]?.asString()
            override val accessToken: String = tokenResponse.accessToken
            override val refreshToken: String? = tokenResponse.refreshToken
        }
    }
}