package hernanbosqued.frontend.viewmodel.auth.impl

import hernanbosqued.domain.UserData
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.flow.StateFlow

open class AuthViewModelImpl(
    private val authUseCase: AuthUseCase
) : AuthViewModel {

    override val userData: StateFlow<UserData?> = authUseCase.userData
    override suspend fun login(authCode: String?) = authUseCase.login(authCode)
    override suspend fun logout() = authUseCase.logout()
    override suspend fun sendTotp(totp: Int) = authUseCase.sendTotp(totp)
}
