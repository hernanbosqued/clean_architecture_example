package hernanbosqued.frontend.viewmodel.task

import hernanbosqued.domain.IdTask
import kotlinx.coroutines.flow.StateFlow

interface TaskUseCase {
    val tasks: StateFlow<List<IdTask>>
}
