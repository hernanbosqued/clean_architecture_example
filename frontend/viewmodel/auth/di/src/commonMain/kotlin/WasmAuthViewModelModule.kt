package hernanbosqued.frontend.viewmodel.auth.di

import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import hernanbosqued.frontend.viewmodel.auth.WasmAuthViewModel
import hernanbosqued.frontend.viewmodel.auth.impl.WasmAuthViewModelImpl
import org.koin.dsl.bind
import org.koin.dsl.module

object WasmAuthViewModelModule {
    fun getModule() =
        module {
            single<WasmAuthViewModel> {
                WasmAuthViewModelImpl(
                    wasmAuthUseCase = get(),
                    coroutineScope = get(),
                )
            }.bind(AuthViewModel::class)
        }
}