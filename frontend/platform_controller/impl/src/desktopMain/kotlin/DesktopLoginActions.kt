package hernanbosqued.frontend.platform_controller.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.platform_controller.LoginActionsBase
import java.awt.Desktop
import java.net.URI

class DesktopLoginActions(
    clientId: String,
    redirectUri: String,
    scopes: List<String>,
    frontendRepository: FrontendRepository,
) : LoginActionsBase(clientId, redirectUri, scopes, frontendRepository) {
    override suspend fun login() {
        userData.emit(openPageAndWaitForResponse())
    }

    suspend fun openPageAndWaitForResponse(): UserData {
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
