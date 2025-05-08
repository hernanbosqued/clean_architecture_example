package hernanbosqued.frontend.viewmodel.task

import hernanbosqued.domain.IdTask
import kotlinx.coroutines.flow.StateFlow

interface TaskViewModel {
    val tasks: StateFlow<List<IdTask>>
}
