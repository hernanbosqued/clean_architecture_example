package hernanbosqued.frontend.platform_controller.di

import hernanbosqued.domain.Persistence
import hernanbosqued.frontend.platform_controller.impl.HttpClientFactory
import hernanbosqued.frontend.platform_controller.impl.PersistenceImpl
import hernanbosqued.frontend.platform_controller.impl.invalidateToken
import hernanbosqued.frontend.usecase.auth.AuthUseCase
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

object CommonPlatformControllerModule {
    fun getModule() =
        module {
            single {
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    classDiscriminator = "type"
                }
            }

            single<HttpClient> {
                HttpClientFactory.create(
                    tokensProviderFunc = {
                        val authUseCase: AuthUseCase = get()
                        authUseCase.provideTokens()
                    }, refreshTokenFunc = {
                        val authUseCase: AuthUseCase = get()
                        authUseCase.refreshTokens()
                    }
                )
            }

            single<() -> Unit>(named("TOKEN_INVALIDATOR")){
                get<HttpClient>()::invalidateToken
            }

            single<Persistence> {
                PersistenceImpl(
                    settings = get(),
                    json = get()
                )
            }
        }
}
