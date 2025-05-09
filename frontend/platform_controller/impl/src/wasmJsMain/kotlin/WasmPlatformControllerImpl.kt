package hernanbosqued.frontend.platform_controller.impl

import hernanbosqued.frontend.usecase.auth.WasmPlatformController
import kotlinx.browser.window

class WasmPlatformControllerImpl : WasmPlatformController {
    override fun openPage(url: String) {
        val authorizationUrl = url
        window.location.href = authorizationUrl
    }
}
