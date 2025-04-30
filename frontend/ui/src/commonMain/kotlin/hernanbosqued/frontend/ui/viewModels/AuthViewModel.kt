package hernanbosqued.frontend.ui.viewModels

import hernanbosqued.frontend.ui.AuthState
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    val authUseCase: AuthUseCase,
    val coroutineScope: CoroutineScope,
) {
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    fun login() {
        coroutineScope.launch {
            val authCode = authUseCase.step1()
            authCode
            _authState.value = AuthState(true)
        }
    }

    fun logout() {
        _authState.value = AuthState()
    }

    fun clean() = coroutineScope.cancel()
}
