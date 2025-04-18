package hernanbosqued.backend.service.di

import hernanbosqued.backend.service.public.Service
import hernanbosqued.backend.service.impl.ServiceImpl
import org.koin.dsl.module

object ServiceModule{
    fun getModule() = module {
        single<Service> {
            ServiceImpl(
                repository = get()
            )
        }
    }
}