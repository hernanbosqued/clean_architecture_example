import hernanbosqued.backend.use_case.auth.AuthUseCase
import hernanbosqued.domain.AuthApiGateway
import hernanbosqued.domain.AuthCodeRequest
import hernanbosqued.domain.UserData
import hernanbosqued.domain.dto.DTOAuthRefreshTokenRequest

class AuthUseCaseImpl(
    private val googleApiGateway: AuthApiGateway,
) : AuthUseCase {
    override suspend fun getUserData(code: AuthCodeRequest): UserData = googleApiGateway.getUserData(code)

    override suspend fun refreshToken(code: DTOAuthRefreshTokenRequest): UserData = googleApiGateway.refreshToken(code)
}
