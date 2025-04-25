package hernanbosqued.frontend.repository.di

import hernanbosqued.frontend.repository.Repository // Añadir este import
import org.koin.dsl.module

object RepositoryModule {
    fun getModule(apiUrl: String) =
        module {
            single<Repository> {
                Repository(apiUrl)
            }
        }
}
