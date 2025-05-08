package hernanbosqued.backend.use_case.db.impl

import hernanbosqued.backend.use_case.db.DbUseCase
import hernanbosqued.domain.DbController
import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task

class DbUseCaseImpl(
    private val dbController: DbController,
) : DbUseCase {
    override fun allTasks(userId: String): List<IdTask> {
        return dbController.allTasks(userId)
    }

    override fun addTask(userId: String, task: Task) {
        dbController.addTask(userId, task)
    }

    override fun removeTask(id: Long): Boolean {
        return dbController.removeTask(id)
    }

    override fun tasksByPriority(userId: String, priority: Priority): List<IdTask> {
        return dbController.tasksByPriority(userId, priority)
    }
}
