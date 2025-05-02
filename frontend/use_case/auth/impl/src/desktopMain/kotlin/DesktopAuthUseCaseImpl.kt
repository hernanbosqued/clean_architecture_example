package hernanbosqued.frontend.usecase.auth.impl

import hernanbosqued.domain.UserData
import hernanbosqued.frontend.platform_controller.LoginActions
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import kotlinx.coroutines.flow.SharedFlow

class DesktopAuthUseCaseImpl(
    val loginActions: LoginActions,
) : AuthUseCase {
    override val userData: SharedFlow<UserData?> = loginActions.userData

    override suspend fun login() = loginActions.login()
}
