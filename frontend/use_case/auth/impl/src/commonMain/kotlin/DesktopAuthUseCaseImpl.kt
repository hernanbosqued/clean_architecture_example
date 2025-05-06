package hernanbosqued.frontend.use_case.auth.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.frontend.usecase.auth.DesktopPlatformController
import hernanbosqued.domain.Persistence

class DesktopAuthUseCaseImpl(
    clientId: String,
    redirectUri: String,
    scopes: List<String>,
    frontendRepository: FrontendRepository,
    authPersistence: Persistence,
    val desktopPlatformController: DesktopPlatformController,
) : BaseAuthUseCase(clientId, redirectUri, scopes, frontendRepository, authPersistence) {
    override suspend fun login() {
        val parameters = desktopPlatformController.openPageAndWaitForResponse(super.generateAuthorizationUrl())
        getUserDataFromAuthCode(requireNotNull(parameters["code"]))
    }
}
