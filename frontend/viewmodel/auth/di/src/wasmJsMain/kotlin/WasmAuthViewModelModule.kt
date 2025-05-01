package hernanbosqued.frontend.viewmodel.auth.di

import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import hernanbosqued.frontend.viewmodel.auth.impl.WasmAuthViewModel
import org.koin.dsl.module

object WasmAuthViewModelModule {
    fun getModule() =
        module {
            single<AuthViewModel> {
                println("Atlanta 5")
                WasmAuthViewModel(get())
            }
        }
}
