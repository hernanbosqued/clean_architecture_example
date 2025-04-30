package hernanbosqued.frontend.ui

import hernanbosqued.frontend.ui.viewModels.AuthViewModel
import hernanbosqued.frontend.usecase.auth.impl.WasmAuthUseCaseImpl

class WasmAuthViewModel(
    val desktopAuthUseCaseImpl: WasmAuthUseCaseImpl,
) : AuthViewModel(desktopAuthUseCaseImpl) {
    override suspend fun login() {
        desktopAuthUseCaseImpl.openPage()
    }
}
