package hernanbosqued.frontend.usecase.auth.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.frontend.platform_controller.LoginActionsBase
import hernanbosqued.frontend.platform_controller.WasmLoginActions
import io.ktor.http.Url
import kotlinx.browser.window

class WasmLoginActions(
    clientId: String,
    redirectUri: String,
    scopes: List<String>,
    frontendRepository: FrontendRepository,
) : LoginActionsBase(clientId, redirectUri, scopes, frontendRepository), WasmLoginActions {
    override suspend fun login() {
        val authorizationUrl = generateAuthorizationUrl(clientId, redirectUri, scopes)
        window.location.href = authorizationUrl
    }

    override suspend fun getUserDataFromUrl(urlString: String) {
        val fullUrlString = "http://dummy.com$urlString"
        val url = Url(fullUrlString)
        val params = url.parameters
        val authCode = params["code"]
        userData.emit(sendAuthCode(authCode!!))
    }
}
