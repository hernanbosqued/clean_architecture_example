package hernanbosqued.frontend.use_case.task.di

import hernanbosqued.frontend.use_case.task.impl.TaskUseCaseImpl
import hernanbosqued.frontend.viewmodel.task.TaskUseCase
import org.koin.dsl.module

object TaskUseCaseModule {
    fun getModule() =
        module {
            single<TaskUseCase> {
                TaskUseCaseImpl(get())
            }
        }
}
