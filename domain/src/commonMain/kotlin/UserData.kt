package hernanbosqued.domain

interface UserData : AuthData {
    val name: String?
    val email: String
    val pictureUrl: String?
}

