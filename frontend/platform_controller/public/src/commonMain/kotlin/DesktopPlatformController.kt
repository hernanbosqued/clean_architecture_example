package hernanbosqued.frontend.platform_controller

import io.ktor.http.Parameters

interface DesktopPlatformController {
    suspend fun openPageAndWaitForResponse(url: String): Parameters
}
