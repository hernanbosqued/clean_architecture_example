package hernanbosqued.frontend.viewmodel.auth.impl

import hernanbosqued.domain.UserData
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

open class AuthViewModelImpl(
    private val authUseCase: AuthUseCase,
    coroutineScope: CoroutineScope,
) : AuthViewModel {
    private val _authState = MutableStateFlow<UserData?>(null)
    override val authState: StateFlow<UserData?> = _authState.asStateFlow()

    init {
        authUseCase.userData
            .onEach { _authState.value = it }
            .launchIn(coroutineScope)
    }

    override suspend fun login() = authUseCase.login()

    override suspend fun logout() {
        _authState.value = null
    }

    override fun getButtonText(): String {
        return if (authState.value != null) "Logout" else "Login with Google"
    }

    override suspend fun getButtonFunction() {
        return (if (authState.value != null) logout() else login())
    }
}
