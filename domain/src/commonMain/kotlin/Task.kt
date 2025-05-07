package hernanbosqued.domain

interface Task {
    val userId: String
    val name: String
    val description: String
    val priority: Priority
}