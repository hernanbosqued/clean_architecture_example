package hernanbosqued.frontend.use_case.task.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Persistence
import hernanbosqued.frontend.viewmodel.task.TaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn


class TaskUseCaseImpl(
    private val frontendRepository: FrontendRepository,
    coroutineScope: CoroutineScope
) : TaskUseCase {

    override val tasks: StateFlow<List<IdTask>> = frontendRepository.userData.map { userData ->
        if (userData == null) return@map emptyList()
        frontendRepository.allTasks()
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}
