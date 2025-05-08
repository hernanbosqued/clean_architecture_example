package hernanbosqued.frontend.use_case.task.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Persistence
import hernanbosqued.frontend.viewmodel.task.TaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn


class TaskUseCaseImpl(
    persistence: Persistence,
    private val frontendRepository: FrontendRepository,
    coroutineScope: CoroutineScope
) : TaskUseCase {

    override val tasks: StateFlow<List<IdTask>> = persistence.userData.mapNotNull { userData ->
        println("TaskUseCaseImpl: persistence.userData emitted -----> $userData")
        val tasks = frontendRepository.allTasks()
        tasks
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )
}
