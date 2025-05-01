package hernanbosqued.frontend.viewmodel.auth.impl

import hernanbosqued.domain.UserData
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import io.ktor.http.Url
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class AuthViewModelBase(
    val authUseCase: AuthUseCase,
) : AuthViewModel {
    protected val authStateMutable = MutableStateFlow<UserData?>(null)
    override val authState: StateFlow<UserData?> = authStateMutable.asStateFlow()

    abstract override suspend fun login()

    override suspend fun processUrl(urlString: String) {
        val fullUrlString = "http://dummy.com$urlString"
        val url = Url(fullUrlString)
        val params = url.parameters
        val authCode = params["code"]
        val userData = authUseCase.sendAuthCode(authCode!!)
        authStateMutable.value = userData
    }

    override fun logout() {
        authStateMutable.value = null
    }
}
