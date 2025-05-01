package hernanbosqued.backend.use_case.auth

import hernanbosqued.domain.TokenRequest
import hernanbosqued.domain.UserData

interface AuthUseCase {
    suspend fun getUserData(code: TokenRequest): UserData
}
