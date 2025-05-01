package hernanbosqued.frontend.usecase.auth.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.frontend.usecase.auth.WasmAuthUseCase
import kotlinx.browser.window

class WasmAuthUseCaseImpl(
    override val clientId: String,
    override val redirectUri: String,
    override val scopes: List<String>,
    override val repository: FrontendRepository,
) : WasmAuthUseCase {
    override fun openPage() {
        val authorizationUrl = generateAuthorizationUrl(clientId, redirectUri, scopes)
        window.location.href = authorizationUrl
    }
}
