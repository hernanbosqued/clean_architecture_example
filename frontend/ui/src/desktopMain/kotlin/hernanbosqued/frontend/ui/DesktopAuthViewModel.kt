package hernanbosqued.frontend.ui

import hernanbosqued.frontend.ui.viewModels.AuthViewModel
import hernanbosqued.frontend.usecase.auth.impl.DesktopAuthUseCaseImpl

class DesktopAuthViewModel(
    val desktopAuthUseCaseImpl: DesktopAuthUseCaseImpl,
) : AuthViewModel(desktopAuthUseCaseImpl) {
    override suspend fun login() {
        authStateMutable.value = desktopAuthUseCaseImpl.openPageAndWaitForResponse()
    }
}
