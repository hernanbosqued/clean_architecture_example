package hernanbosqued.frontend.repository.di

import hernanbosqued.frontend.repository.Repository // Añadir este import
import hernanbosqued.frontend.repository.impl.RepositoryImpl
import org.koin.dsl.module

object RepositoryModule {
    fun getModule(apiUrl: String) =
        module {
            single<Repository> {
                RepositoryImpl(apiUrl)
            }
        }
}
