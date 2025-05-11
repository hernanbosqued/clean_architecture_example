package hernanbosqued.frontend.use_case.task.impl

import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.Persistence
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task
import hernanbosqued.domain.TasksState
import hernanbosqued.frontend.viewmodel.task.TaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class TaskUseCaseImpl(
    private val frontendRepository: FrontendRepository,
    persistence: Persistence,
    coroutineScope: CoroutineScope
) : TaskUseCase {

    private sealed class Operation {
        object LoadList : Operation()
        data class AddNewTask(val task: Task) : Operation()
        data class RemoveTask(val taskId: Long) : Operation()
    }

    private val operationTrigger = MutableSharedFlow<Operation>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    ).also { it.tryEmit(Operation.LoadList) }

    override val tasksState: StateFlow<TasksState> = combine(
        flow = persistence.userData,
        flow2 = operationTrigger
    ) { userData, isProcessing -> userData to isProcessing }
        .flatMapLatest { (userData, operation) ->
            flow {
                if (userData == null) {
                    emit(TasksState(isLoading = false, tasks = emptyList()))
                } else {
                    emit(TasksState(isLoading = true, tasks = tasksState.value.tasks))

                    when (operation) {
                        is Operation.AddNewTask -> frontendRepository.addTask(operation.task)
                        is Operation.RemoveTask -> frontendRepository.removeTask(operation.taskId)
                        is Operation.LoadList -> Unit
                    }
                    emit(TasksState(isLoading = false, tasks = frontendRepository.allTasks()))
                }
            }
        }
        .onEach { println(it) }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = TasksState(isLoading = true)
        )

    override suspend fun addTask(name: String, description: String, priority: Priority) {
        val newTask = object : Task {
            override val name: String = name
            override val description: String = description
            override val priority: Priority = priority
        }
        operationTrigger.tryEmit(Operation.AddNewTask(newTask))
    }

    override suspend fun removeTask(taskId: Long) {
        operationTrigger.tryEmit(Operation.RemoveTask(taskId))
    }

    override suspend fun refreshTasks() {
        operationTrigger.tryEmit(Operation.LoadList)
    }
}
