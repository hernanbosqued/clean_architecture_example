package hernanbosqued.frontend.viewmodel.auth.di

import hernanbosqued.frontend.usecase.auth.DesktopAuthUseCase
import hernanbosqued.frontend.viewmodel.auth.impl.AuthViewModelBase

class DesktopAuthViewModel(
    val desktopAuthUseCaseImpl: DesktopAuthUseCase,
) : AuthViewModelBase(desktopAuthUseCaseImpl) {
    override suspend fun login() {
        authStateMutable.value = desktopAuthUseCaseImpl.openPageAndWaitForResponse()
    }
}
