package hernanbosqued.frontend.usecase.auth.di

import hernanbosqued.frontend.usecase.auth.AuthUseCase
import hernanbosqued.frontend.usecase.auth.impl.WasmAuthUseCaseImpl
import org.koin.dsl.module

object WasmAuthUseCaseModule {
    fun getModule() =
        module {
            single<AuthUseCase> { params ->
                WasmAuthUseCaseImpl(
                    clientId = params.get(),
                    redirectUri = params.get(),
                    scopes = params.get(),
                )
            }
        }
}
