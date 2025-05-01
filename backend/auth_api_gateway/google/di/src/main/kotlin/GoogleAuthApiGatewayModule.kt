package hernanbosqued.backend.auth_api_gateway.google.di

import hernanbosqued.backend.auth_api_gateway_google.impl.GoogleAuthApiGateway
import hernanbosqued.domain.AuthApiGateway
import io.ktor.client.HttpClient
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation as ContentNegotiationClient

object GoogleAuthApiGatewayModule {
    fun getModule() =
        module {
            factory<HttpClient> {
                HttpClient {
                    install(plugin = ContentNegotiationClient) {
                        json()
                    }
                }
            }
            single<AuthApiGateway> {
                GoogleAuthApiGateway(
                    httpClient = get(),
                )
            }
        }
}
