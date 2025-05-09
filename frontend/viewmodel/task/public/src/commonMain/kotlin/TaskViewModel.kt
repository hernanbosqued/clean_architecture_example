package hernanbosqued.frontend.viewmodel.task

import hernanbosqued.domain.Priority
import hernanbosqued.domain.TasksState
import kotlinx.coroutines.flow.StateFlow

interface TaskViewModel {
    val tasksState: StateFlow<TasksState>

    suspend fun addTask(name: String, description: String, priority: Priority)
    suspend fun removeTask(taskId: Long)
}
