import hernanbosqued.backend.use_case.auth.AuthUseCase
import hernanbosqued.domain.AuthApiGateway
import hernanbosqued.domain.TokenRequest
import hernanbosqued.domain.UserData

class AuthUseCaseImpl(
    private val googleApiGateway: AuthApiGateway,
) : AuthUseCase {
    override suspend fun getUserData(code: TokenRequest): UserData = googleApiGateway.getUserData(code)
}
