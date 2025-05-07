package hernanbosqued.backend.db_controller.json

import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority

data class DAOTask(
    override val id: Long,
    override val userId: String,
    override val name: String,
    override val description: String,
    override val priority: Priority,
) : IdTask
