package hernanbosqued.frontend.viewmodel.auth.di

import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import hernanbosqued.frontend.viewmodel.auth.impl.AuthViewModelImpl
import org.koin.dsl.module

object DesktopAuthViewModelModule {
    fun getModule() =
        module {
            single<AuthViewModel> {
                AuthViewModelImpl(
                    authUseCase = get(),
                    coroutineScope = get(),
                )
            }
        }
}
