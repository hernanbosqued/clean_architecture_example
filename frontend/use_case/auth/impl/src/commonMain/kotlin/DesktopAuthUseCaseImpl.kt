package hernanbosqued.frontend.use_case.auth.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.Persistence
import hernanbosqued.frontend.usecase.auth.DesktopPlatformController

class DesktopAuthUseCaseImpl(
    clientId: String,
    redirectUri: String,
    scopes: List<String>,
    frontendRepository: FrontendRepository,
    val desktopPlatformController: DesktopPlatformController,
) : BaseAuthUseCase(clientId, redirectUri, scopes, frontendRepository) {

    override suspend fun login() {
        val parameters = desktopPlatformController.openPageAndWaitForResponse(super.generateAuthorizationUrl())
        getUserDataFromAuthCode(requireNotNull(parameters["code"]))
    }

}
