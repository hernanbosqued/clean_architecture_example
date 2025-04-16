import hernanbosqued.backend.entities.IdTask
import hernanbosqued.backend.entities.Priority

data class DAOTask(
    override val id: Int,
    override val name: String,
    override val description: String,
    override val priority: Priority
): IdTask