package hernanbosqued.frontend.use_case.auth.di

import hernanbosqued.frontend.use_case.auth.impl.WasmAuthUseCaseImpl
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import org.koin.dsl.module

object WasmAuthUseCaseModule {
    fun getModule(
        clientId: String,
        redirectUri: String,
        scopes: List<String>,
    ) =
        module {
            single<AuthUseCase> {
                WasmAuthUseCaseImpl(
                    clientId = clientId,
                    redirectUri = redirectUri,
                    scopes = scopes,
                    wasmPlatformController = get(),
                    frontendRepository = get(),
                )
            }
        }
}