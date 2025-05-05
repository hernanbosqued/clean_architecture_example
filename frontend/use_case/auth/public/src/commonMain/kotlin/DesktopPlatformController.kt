package hernanbosqued.frontend.usecase.auth

import io.ktor.http.Parameters

interface DesktopPlatformController {
    suspend fun openPageAndWaitForResponse(url: String): Parameters
}
