package hernanbosqued.frontend.viewmodel.task

import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import kotlinx.coroutines.flow.StateFlow

interface TaskUseCase {
    val tasks: StateFlow<List<IdTask>>
    suspend fun addTask(name: String, description: String, priority: Priority)
}
