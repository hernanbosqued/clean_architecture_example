package hernanbosqued.frontend.viewmodel.task.di

import hernanbosqued.frontend.viewmodel.task.TaskViewModel
import hernanbosqued.frontend.viewmodel.task.impl.TaskViewModelImpl
import org.koin.dsl.module

object TaskViewModelModule {
    fun getModule() =
        module {
            single<TaskViewModel> {
                println("Atlanta 2")
                TaskViewModelImpl(
                    taskUseCase = get()
                )
            }
        }
}
