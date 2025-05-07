package hernanbosqued.backend.use_case.auth

import hernanbosqued.domain.AuthCodeRequest
import hernanbosqued.domain.UserData
import hernanbosqued.domain.dto.DTOAuthRefreshTokenRequest

interface AuthUseCase {
    suspend fun getUserData(code: AuthCodeRequest): UserData

    suspend fun refreshToken(code: DTOAuthRefreshTokenRequest): UserData
}
