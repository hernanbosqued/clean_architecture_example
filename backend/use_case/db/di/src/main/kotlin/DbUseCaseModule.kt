package hernanbosqued.backend.use_case.db.di

import hernanbosqued.backend.use_case.db.DbUseCase
import hernanbosqued.backend.use_case.db.impl.DbUseCaseImpl
import org.koin.dsl.module

object DbUseCaseModule {
    fun getModule() =
        module {
            single<DbUseCase> {
                DbUseCaseImpl(
                    dbController = get(),
                )
            }
        }
}
