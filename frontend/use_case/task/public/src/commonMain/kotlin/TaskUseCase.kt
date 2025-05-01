package hernanbosqued.frontend.viewmodel.task

import hernanbosqued.domain.IdTask

interface TaskUseCase {
    suspend fun allTasks(): List<IdTask>
}
