package hernanbosqued.backend.presenter.di

import hernanbosqued.backend.presenter.Presenter
import hernanbosqued.backend.presenter.impl.PresenterImpl
import org.koin.dsl.module

object PresenterModule {
    fun getModule() =
        module {
            single<Presenter> {
                PresenterImpl(
                    service = get(),
                )
            }
        }
}
