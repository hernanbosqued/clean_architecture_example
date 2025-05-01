package hernanbosqued.frontend.usecase.auth.di

import hernanbosqued.frontend.usecase.auth.WasmAuthUseCase
import hernanbosqued.frontend.usecase.auth.impl.WasmAuthUseCaseImpl
import org.koin.dsl.module

object WasmAuthUseCaseModule {
    fun getModule(
        clientId: String,
        redirectUri: String,
        scopes: List<String>,
    ) = module {
        single<WasmAuthUseCase> {
            println("Atlanta 6")
            WasmAuthUseCaseImpl(
                clientId = clientId,
                redirectUri = redirectUri,
                scopes = scopes,
                repository = get(),
            )
        }
    }
}
