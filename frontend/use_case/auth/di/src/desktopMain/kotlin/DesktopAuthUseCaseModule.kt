package hernanbosqued.frontend.usecase.auth.di

import hernanbosqued.frontend.usecase.auth.DesktopAuthUseCase
import hernanbosqued.frontend.usecase.auth.impl.DesktopAuthUseCaseImpl
import org.koin.dsl.module

object DesktopAuthUseCaseModule {
    fun getModule(
        clientId: String,
        redirectUri: String,
        scopes: List<String>,
    ) = module {
        single<DesktopAuthUseCase> {
            DesktopAuthUseCaseImpl(
                clientId = clientId,
                redirectUri = redirectUri,
                scopes = scopes,
                repository = get(),
            )
        }
    }
}
