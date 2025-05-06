package hernanbosqued.domain

interface AuthCodeRequest {
    val clientId: String
    val redirectUri: String
    val authorizationCode: String
}
