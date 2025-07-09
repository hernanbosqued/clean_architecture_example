package hernanbosqued.backend.auth_api_gateway_google.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.warrenstrange.googleauth.GoogleAuthenticator
import hernanbosqued.constants.Constants
import hernanbosqued.domain.AuthApiGateway
import hernanbosqued.domain.AuthCodeRequest
import hernanbosqued.domain.AuthRefreshTokenRequest
import hernanbosqued.domain.DbController
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
    val dbController: DbController
) : AuthApiGateway {
    override suspend fun getUserData(code: AuthCodeRequest): UserData {
        val tokenResponse = getGoogleTokens(
            grantType = "authorization_code",
            clientId = Constants.GOOGLE_CLIENT,
            clientSecret = Constants.GOOGLE_SECRET,
            redirectUri = code.redirectUri,
            authorizationCode = code.authorizationCode,
        )

        return extractUserDataFromIdToken(tokenResponse)
    }

    override suspend fun refreshToken(code: AuthRefreshTokenRequest): UserData {
        val tokenResponse = getGoogleTokens(
            grantType = "refresh_token",
            clientId = Constants.GOOGLE_CLIENT,
            clientSecret = Constants.GOOGLE_SECRET,
            refreshToken = code.refreshToken,
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
            override val idToken: String = tokenResponse.idToken
            override val refreshToken: String? = tokenResponse.refreshToken
            override val mfaSecret: String = getMfaSecret(jwt.claims["sub"]?.asString() ?: throw RuntimeException("sub must exist in token"))
            override val isMfaAuthenticated: Boolean = false

            private fun getMfaSecret(userId: String): String {
                var mfaSecret = dbController.getMfaSecret(userId)

                if (mfaSecret != null) {
                    return mfaSecret
                } else {
                    mfaSecret = GoogleAuthenticator().createCredentials().key
                    dbController.addMfaSecret(userId, mfaSecret)
                    return mfaSecret
                }
            }
        }
    }
}
