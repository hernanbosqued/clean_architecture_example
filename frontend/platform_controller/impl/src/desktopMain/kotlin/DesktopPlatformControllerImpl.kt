package hernanbosqued.frontend.platform_controller.impl

import hernanbosqued.frontend.usecase.auth.DesktopPlatformController
import io.ktor.http.Parameters
import java.awt.Desktop
import java.net.URI

class DesktopPlatformControllerImpl() : DesktopPlatformController {

    override suspend fun openPageAndWaitForResponse(url: String): Parameters {
        val localServer = LocalServer(8082)

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI(url))
        }

        return localServer.waitForParameters()
    }
}
