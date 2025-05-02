package hernanbosqued.frontend.usecase.auth.di

import hernanbosqued.frontend.usecase.auth.WasmAuthUseCase
import hernanbosqued.frontend.usecase.auth.impl.WasmAuthUseCaseImpl
import org.koin.dsl.module

object WasmAuthUseCaseModule {
    fun getModule() =
        module {
            single<WasmAuthUseCase> {
                println("Atlanta 6")
                WasmAuthUseCaseImpl(
                    loginActions = get(),
                )
            }
        }
}
