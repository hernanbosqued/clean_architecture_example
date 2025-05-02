package hernanbosqued.frontend.viewmodel.auth.di

import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import hernanbosqued.frontend.viewmodel.auth.impl.AuthViewModelImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

object AuthViewModelModule {
    fun getModule() =
        module {
            single<AuthViewModel> {
                AuthViewModelImpl(
                    loginActions = get(),
                    coroutineScope = get()
                )
            }
        }
}
