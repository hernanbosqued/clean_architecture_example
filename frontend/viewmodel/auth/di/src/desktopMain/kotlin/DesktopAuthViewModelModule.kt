package hernanbosqued.frontend.viewmodel.auth.di

import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import org.koin.dsl.module

object DesktopAuthViewModelModule {
    fun getModule() =
        module {
            single<AuthViewModel> {
                DesktopAuthViewModel(get())
            }
        }
}
