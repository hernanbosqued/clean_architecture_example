package hernanbosqued.frontend.viewmodel.auth.impl

import hernanbosqued.domain.UserData
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import hernanbosqued.frontend.viewmodel.auth.LoginActions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AuthViewModelImpl(
    private val loginActions: LoginActions,
    coroutineScope: CoroutineScope
) : AuthViewModel {
    private val _authState = MutableStateFlow<UserData?>(null)
    override val authState: StateFlow<UserData?> = _authState.asStateFlow()

    init {
        loginActions.userData
            .onEach { _authState.value = it }
            .launchIn(coroutineScope)
    }

    override suspend fun login() = loginActions.login()

    override suspend fun logout() {
        _authState.value = null
    }

    override fun getButtonText(): String {
        return if (authState.value != null) "Logout" else "Login with Google"
    }

    override suspend fun getButtonFunction(){
        return (if (authState.value != null) logout() else login())
    }
}
