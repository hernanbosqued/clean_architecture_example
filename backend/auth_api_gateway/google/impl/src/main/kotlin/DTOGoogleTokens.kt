package hernanbosqued.backend.auth_api_gateway_google.impl

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DTOGoogleTokens(
    @SerialName("access_token") val accessToken: String,
    @SerialName("id_token") val idToken: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("scope") val scope: String,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("token_type") val tokenType: String,
)
