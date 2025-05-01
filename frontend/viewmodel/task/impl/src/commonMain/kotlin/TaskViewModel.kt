package hernanbosqued.frontend.viewmodel.task.impl

import hernanbosqued.domain.IdTask
import hernanbosqued.frontend.viewmodel.task.TaskUseCase
import hernanbosqued.frontend.viewmodel.task.TaskViewModel

class TaskViewModelImpl(
    private val taskUseCase: TaskUseCase
) : TaskViewModel {
    override suspend fun getTasks(): List<IdTask> = taskUseCase.allTasks()
}