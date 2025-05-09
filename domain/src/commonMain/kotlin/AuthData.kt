package hernanbosqued.domain

interface AuthData{
    val idToken: String
    val refreshToken: String?
}