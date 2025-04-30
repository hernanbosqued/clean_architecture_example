package hernanbosqued.frontend.usecase.auth.impl

import hernanbosqued.backend.domain.UserData
import hernanbosqued.frontend.repository.Repository
import hernanbosqued.frontend.usecase.auth.AuthUseCase

class DesktopAuthUseCaseImpl(
    override val clientId: String,
    override val redirectUri: String,
    override val scopes: List<String>,
    override val repository: Repository,
) : AuthUseCase {
    suspend fun openPageAndWaitForResponse(): UserData {
        val localServer = LocalServer(8082)

        val authorizationUrl = generateAuthorizationUrl(clientId, redirectUri, scopes)
        openWebPage(authorizationUrl)

        val code = localServer.waitForCode()
        val userData = sendAuthCode(code!!)
        return userData
    }
}
