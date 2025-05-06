package hernanbosqued.frontend.use_case.auth.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.frontend.usecase.auth.Persistence
import hernanbosqued.frontend.usecase.auth.WasmPlatformController

class WasmAuthUseCaseImpl(
    clientId: String,
    redirectUri: String,
    scopes: List<String>,
    frontendRepository: FrontendRepository,
    persistence: Persistence,
    val wasmPlatformController: WasmPlatformController,
) : BaseAuthUseCase(clientId, redirectUri, scopes, frontendRepository, persistence) {
    override suspend fun login() {
        wasmPlatformController.openPage(super.generateAuthorizationUrl())
    }
}
