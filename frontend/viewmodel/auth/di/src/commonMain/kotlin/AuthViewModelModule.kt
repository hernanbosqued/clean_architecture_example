package hernanbosqued.frontend.viewmodel.task.di

import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import hernanbosqued.frontend.viewmodel.auth.impl.AuthViewModelImpl
import org.koin.dsl.module

object AuthViewModelModule {
    fun getModule() =
        module {
            single<AuthViewModel> {
                AuthViewModelImpl()
            }
        }
}
