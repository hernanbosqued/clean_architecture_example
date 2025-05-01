package hernanbosqued.domain

interface TokenRequest {
    val clientId: String
    val redirectUri: String
    val authorizationCode: String
}
