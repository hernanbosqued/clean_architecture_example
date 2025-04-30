package hernanbosqued.frontend.ui.viewModels

import hernanbosqued.backend.domain.UserData
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import io.ktor.http.Url
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class AuthViewModel(
    val authUseCase: AuthUseCase,
) {
    protected val authStateMutable = MutableStateFlow<UserData?>(null)
    val authState: StateFlow<UserData?> = authStateMutable.asStateFlow()

    abstract suspend fun login()

    suspend fun processUrl(urlString: String) {
        val fullUrlString = "http://dummy.com$urlString"
        val url = Url(fullUrlString)
        val params = url.parameters
        val authCode = params["code"]
        val userData = authUseCase.sendAuthCode(authCode!!)
        authStateMutable.value = userData
    }

    fun logout() {
        authStateMutable.value = null
    }
}
