package hernanbosqued.frontend.viewmodel.auth.impl

import hernanbosqued.frontend.usecase.auth.WasmAuthUseCase

class WasmAuthViewModel(
    val wasmAuthUseCaseImpl: WasmAuthUseCase,
) : AuthViewModelBase(wasmAuthUseCaseImpl) {
    override suspend fun login() {
        wasmAuthUseCaseImpl.openPage()
    }
}
