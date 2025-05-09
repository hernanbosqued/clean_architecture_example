package hernanbosqued.frontend.use_case.auth.di

import hernanbosqued.frontend.use_case.auth.impl.DesktopAuthUseCaseImpl
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

object DesktopAuthUseCaseModule {
    fun getModule(
        clientId: String,
        redirectUri: String,
        scopes: List<String>,
    ) = module {
        single<AuthUseCase> {
            DesktopAuthUseCaseImpl(
                clientId = clientId,
                redirectUri = redirectUri,
                scopes = scopes,
                desktopPlatformController = get(),
                frontendRepository = get(),
                persistence = get(),
                tokenInvalidator = get(named("TOKEN_INVALIDATOR"))
            )
        }
    }
}
