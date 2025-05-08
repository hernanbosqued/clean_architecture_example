package hernanbosqued.frontend.viewmodel.auth.impl

import hernanbosqued.domain.UserData
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import hernanbosqued.frontend.viewmodel.task.TaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

open class AuthViewModelImpl(
    private val authUseCase: AuthUseCase
) : AuthViewModel {

    override val userData: StateFlow<UserData?> = authUseCase.userData

    override suspend fun login() = authUseCase.login()

    override suspend fun logout() = authUseCase.logout()

    override fun getButtonText(): String = if (userData.value != null) "Logout" else "Login with Google"

    override suspend fun getButtonFunction() = if (userData.value != null) logout() else login()
}
