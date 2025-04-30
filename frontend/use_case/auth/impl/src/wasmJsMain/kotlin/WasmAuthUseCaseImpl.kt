package hernanbosqued.frontend.usecase.auth.impl

import hernanbosqued.frontend.usecase.auth.AuthUseCase

class WasmAuthUseCaseImpl(
    override val clientId: String,
    override val redirectUri: String,
    override val scopes: List<String>,
) : AuthUseCase {
    override suspend fun step1(): String {
        val authorizationUrl = generateAuthorizationUrl(clientId, redirectUri, scopes)
        openWebPage(authorizationUrl)

        return ""
    }
}
