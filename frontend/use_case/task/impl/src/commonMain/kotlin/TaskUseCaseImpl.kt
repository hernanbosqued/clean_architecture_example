package hernanbosqued.frontend.use_case.task.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.IdTask
import hernanbosqued.frontend.viewmodel.task.TaskUseCase

class TaskUseCaseImpl(
    private val frontendRepository: FrontendRepository
) : TaskUseCase {
    override suspend fun allTasks(): List<IdTask> = frontendRepository.allTasks()
}