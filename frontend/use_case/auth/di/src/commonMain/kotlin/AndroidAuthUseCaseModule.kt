package hernanbosqued.frontend.use_case.auth.di

import hernanbosqued.frontend.use_case.auth.impl.AndroidAuthUseCaseImpl
import hernanbosqued.frontend.use_case.auth.impl.WasmAuthUseCaseImpl
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

object AndroidAuthUseCaseModule {
    fun getModule(
        clientId: String,
        redirectUri: String,
        scopes: List<String>,
    ) = module {
        single<AuthUseCase> {
            AndroidAuthUseCaseImpl(
                clientId = clientId,
                redirectUri = redirectUri,
                scopes = scopes,
                androidPlatformController = get(),
                frontendRepository = get(),
                persistence = get(),
                tokenInvalidator = get(named("TOKEN_INVALIDATOR"))
            )
        }
    }
}
