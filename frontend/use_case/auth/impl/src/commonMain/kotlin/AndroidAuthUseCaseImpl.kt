package hernanbosqued.frontend.use_case.auth.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.Persistence

    class AndroidAuthUseCaseImpl(
    clientId: String,
    redirectUri: String,
    scopes: List<String>,
    frontendRepository: FrontendRepository,
    persistence: Persistence,
    tokenInvalidator: ()-> Unit
) : BaseAuthUseCase(clientId, redirectUri, scopes, frontendRepository, persistence, tokenInvalidator) {
    override suspend fun login(authCode: String?) {
        super.getUserDataFromAuthCode(authCode!!)
    }
}
