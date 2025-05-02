package hernanbosqued.frontend.usecase.auth.impl

import hernanbosqued.domain.UserData
import hernanbosqued.frontend.platform_controller.WasmLoginActions
import hernanbosqued.frontend.usecase.auth.WasmAuthUseCase
import kotlinx.coroutines.flow.SharedFlow

class WasmAuthUseCaseImpl(
    val loginActions: WasmLoginActions,
) : WasmAuthUseCase {
    override val userData: SharedFlow<UserData?> = loginActions.userData

    override suspend fun login() = loginActions.login()

    override suspend fun setUserDataFromUrl(urlString: String) = loginActions.getUserDataFromUrl(urlString)
}
