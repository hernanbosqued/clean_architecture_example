package hernanbosqued.domain

interface UserData {
    val name: String?
    val email: String
    val pictureUrl: String?
    val accessToken: String
    val refreshToken: String?
}
