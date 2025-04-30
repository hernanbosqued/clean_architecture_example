package hernanbosqued.frontend.usecase.auth.di

import hernanbosqued.frontend.usecase.auth.AuthUseCase
import hernanbosqued.frontend.usecase.auth.impl.DesktopAuthUseCaseImpl
import org.koin.dsl.module

object DesktopAuthUseCaseModule {
    fun getModule() =
        module {
            single<AuthUseCase> { params ->
                DesktopAuthUseCaseImpl(
                    clientId = params.get(),
                    redirectUri = params.get(),
                    scopes = params.get(),
                    repository = get(),
                )
            }
        }
}
