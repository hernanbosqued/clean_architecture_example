package hernanbosqued.frontend.usecase.auth.impl

import hernanbosqued.frontend.repository.Repository
import hernanbosqued.frontend.usecase.auth.AuthUseCase

class WasmAuthUseCaseImpl(
    override val clientId: String,
    override val redirectUri: String,
    override val scopes: List<String>,
    override val repository: Repository,
) : AuthUseCase {
    fun openPage() {
        val authorizationUrl = generateAuthorizationUrl(clientId, redirectUri, scopes)
        openWebPage(authorizationUrl)
    }
}
