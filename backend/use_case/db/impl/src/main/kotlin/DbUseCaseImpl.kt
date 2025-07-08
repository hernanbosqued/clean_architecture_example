package hernanbosqued.backend.use_case.db.impl

import hernanbosqued.backend.use_case.db.DbUseCase
import hernanbosqued.domain.DbController
import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task

class DbUseCaseImpl(
    private val dbController: DbController,
) : DbUseCase {
    override fun allTasks(userId: String): List<IdTask> = dbController.allTasks(userId)
    override fun addTask(userId: String, task: Task) = dbController.addTask(userId, task)
    override fun removeTask(id: Long): Boolean = dbController.removeTask(id)
    override fun tasksByPriority(userId: String, priority: Priority): List<IdTask> = dbController.tasksByPriority(userId, priority)

    override fun getMfaSecret(userId: String): String? = dbController.getMfaSecret(userId)
    override fun addMfaSecret(userId: String, mfaSecret: String) = dbController.addMfaSecret(userId, mfaSecret)
}
