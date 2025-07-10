package hernanbosqued.domain

interface UserData : AuthData {
    val userId: String
    val name: String
    val email: String
    val pictureUrl: String?
    val totpUri: String
    val totpUriQrCode: String
    val isMfaAuthenticated: Boolean
}