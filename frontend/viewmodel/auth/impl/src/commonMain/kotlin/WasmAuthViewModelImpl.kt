package hernanbosqued.frontend.viewmodel.auth.impl

import hernanbosqued.frontend.usecase.auth.AuthUseCase
import hernanbosqued.frontend.viewmodel.auth.WasmAuthViewModel
import kotlinx.coroutines.CoroutineScope

class WasmAuthViewModelImpl(
    val wasmAuthUseCase: AuthUseCase
) : AuthViewModelImpl(wasmAuthUseCase), WasmAuthViewModel {
    override suspend fun setUserData(authCode: String) {
        wasmAuthUseCase.getUserDataFromAuthCode(authCode)
    }
}
