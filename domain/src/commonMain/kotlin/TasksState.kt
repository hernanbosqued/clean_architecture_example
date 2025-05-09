package hernanbosqued.domain

data class TasksState(
    val isLoading: Boolean = false,
    val tasks: List<IdTask> = emptyList(),
)