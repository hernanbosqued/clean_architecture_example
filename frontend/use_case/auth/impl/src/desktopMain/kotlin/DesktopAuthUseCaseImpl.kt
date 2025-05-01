package hernanbosqued.frontend.usecase.auth.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.usecase.auth.DesktopAuthUseCase
import java.awt.Desktop
import java.net.URI

class DesktopAuthUseCaseImpl(
    override val clientId: String,
    override val redirectUri: String,
    override val scopes: List<String>,
    override val repository: FrontendRepository,
) : DesktopAuthUseCase {
    override suspend fun openPageAndWaitForResponse(): UserData {
        val localServer = LocalServer(8082)

        val authorizationUrl = generateAuthorizationUrl(clientId, redirectUri, scopes)

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI(authorizationUrl))
        }

        val code = localServer.waitForCode()
        val userData = sendAuthCode(code!!)
        return userData
    }
}
