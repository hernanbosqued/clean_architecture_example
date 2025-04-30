package hernanbosqued.frontend.usecase.auth.impl

import hernanbosqued.frontend.usecase.auth.AuthUseCase

class DesktopAuthUseCaseImpl(
    override val clientId: String,
    override val redirectUri: String,
    override val scopes: List<String>,
) : AuthUseCase {
    override suspend fun step1(): String? {
        val localServer = LocalServer(8082)

        val authorizationUrl = generateAuthorizationUrl(clientId, redirectUri, scopes)
        openWebPage(authorizationUrl)

        return localServer.waitForCode()
    }
}
