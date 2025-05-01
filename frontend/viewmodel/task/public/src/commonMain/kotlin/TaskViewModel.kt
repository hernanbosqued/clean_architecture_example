package hernanbosqued.frontend.viewmodel.task

import hernanbosqued.domain.IdTask

interface TaskViewModel {
    suspend fun getTasks(): List<IdTask>
}
