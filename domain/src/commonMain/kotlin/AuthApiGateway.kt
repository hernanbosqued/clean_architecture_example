package hernanbosqued.domain

interface AuthApiGateway {
    suspend fun getUserData(code: AuthCodeRequest): UserData

    suspend fun refreshToken(code: AuthRefreshTokenRequest): UserData
}
