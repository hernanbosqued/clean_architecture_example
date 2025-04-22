package hernanbosqued.backend.domain

interface Task {
    val name: String
    val description: String
    val priority: Priority
}
