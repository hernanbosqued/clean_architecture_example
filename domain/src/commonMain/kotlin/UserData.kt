package hernanbosqued.domain

interface UserData {
    val name: String?
    val email: String
    val pictureUrl: String?
    val idToken: String
    val refreshToken: String?
}
