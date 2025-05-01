package hernanbosqued.frontend.repository.di

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.frontend.repository.impl.FrontendRepositoryImpl
import org.koin.dsl.module

object RepositoryModule {
    fun getModule(apiUrl: String) =
        module {
            single<FrontendRepository> {
                println("Atlanta 3")
                FrontendRepositoryImpl(apiUrl)
            }
        }
}
