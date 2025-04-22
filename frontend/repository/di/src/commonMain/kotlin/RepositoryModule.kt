package hernanbosqued.frontend.repository.di

import hernanbosqued.frontend.repository.Repository // Añadir este import
import org.koin.dsl.module

object RepositoryModule {
    fun getModule() =
        module {
            single {
//            val retrofit = Retrofit.Builder()
//                .baseUrl("https://localhost:8080/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            retrofit.create(RepositoryInterface::class.java)
            }
//
            single<Repository> {
                Repository()
            }
        }
}
