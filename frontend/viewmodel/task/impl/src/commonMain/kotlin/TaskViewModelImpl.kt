package hernanbosqued.frontend.viewmodel.task.impl

import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.viewmodel.task.TaskUseCase
import hernanbosqued.frontend.viewmodel.task.TaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TaskViewModelImpl(
    private val taskUseCase: TaskUseCase,
) : TaskViewModel {

    override val tasks: StateFlow<List<IdTask>> = taskUseCase.tasks

    override suspend fun addTask(name: String, description: String, priority: Priority) {
        taskUseCase.addTask(name, description, priority)
    }

    override suspend fun removeTask(taskId: Long) {
        taskUseCase.removeTask(taskId)
    }
}
