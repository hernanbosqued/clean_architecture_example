package hernanbosqued.frontend.viewmodel.task.impl

import hernanbosqued.domain.Priority
import hernanbosqued.domain.TasksState
import hernanbosqued.frontend.viewmodel.task.TaskUseCase
import hernanbosqued.frontend.viewmodel.task.TaskViewModel
import kotlinx.coroutines.flow.StateFlow

class TaskViewModelImpl(
    private val taskUseCase: TaskUseCase,
) : TaskViewModel {

    override val tasksState: StateFlow<TasksState> = taskUseCase.tasksState

    override suspend fun addTask(name: String, description: String, priority: Priority) {
        taskUseCase.addTask(name, description, priority)
    }

    override suspend fun removeTask(taskId: Long) {
        taskUseCase.removeTask(taskId)
    }

    override suspend fun refreshTasks() {
        taskUseCase.refreshTasks()
    }
}
