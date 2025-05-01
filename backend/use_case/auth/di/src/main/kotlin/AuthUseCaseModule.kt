package hernanbosqued.backend.auth.di

import AuthUseCaseImpl
import hernanbosqued.backend.use_case.auth.AuthUseCase
import org.koin.dsl.module

object AuthUseCaseModule {
    fun getModule() =
        module {
            single<AuthUseCase> {
                AuthUseCaseImpl(get())
            }
        }
}
