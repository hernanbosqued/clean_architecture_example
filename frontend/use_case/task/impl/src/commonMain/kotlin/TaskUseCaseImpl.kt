package hernanbosqued.frontend.use_case.task.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Persistence
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task
import hernanbosqued.frontend.viewmodel.task.TaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn

class TaskUseCaseImpl(
    private val frontendRepository: FrontendRepository,
    coroutineScope: CoroutineScope
) : TaskUseCase {

    private val refreshTasksTrigger = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).also { it.tryEmit(Unit) }

    override val tasks: StateFlow<List<IdTask>> = combine(
        flow = frontendRepository.userData,
        flow2 = refreshTasksTrigger
    ) { userData, _ ->
        if (userData == null) {
            emptyList()
        } else {
            frontendRepository.allTasks()
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    override suspend fun addTask(name: String, description: String, priority: Priority) {
        frontendRepository.addTask(object : Task {
            override val name: String = name
            override val description: String = description
            override val priority: Priority = priority
        })
        refreshTasksTrigger.tryEmit(Unit)
    }

    override suspend fun removeTask(taskId: Long) {
        frontendRepository.removeTask(taskId)
        refreshTasksTrigger.tryEmit(Unit)
    }
}
