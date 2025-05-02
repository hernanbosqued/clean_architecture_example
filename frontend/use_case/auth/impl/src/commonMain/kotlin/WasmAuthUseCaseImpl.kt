package hernanbosqued.frontend.use_case.auth.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.frontend.platform_controller.WasmPlatformController

class WasmAuthUseCaseImpl(
    clientId: String,
    redirectUri: String,
    scopes: List<String>,
    frontendRepository: FrontendRepository,
    val wasmPlatformController: WasmPlatformController
) : BaseAuthUseCase(clientId, redirectUri, scopes, frontendRepository) {

    override suspend fun login() = wasmPlatformController.openPage(super.generateAuthorizationUrl())
}