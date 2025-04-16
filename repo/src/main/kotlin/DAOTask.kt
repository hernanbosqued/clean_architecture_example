package hernanbosqued.backend.repo

import hernanbosqued.backend.domain.IdTask
import hernanbosqued.backend.domain.Priority

data class DAOTask(
    override val id: Int,
    override val name: String,
    override val description: String,
    override val priority: Priority
): IdTask