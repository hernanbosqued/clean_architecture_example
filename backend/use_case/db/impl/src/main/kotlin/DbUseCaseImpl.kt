package hernanbosqued.backend.use_case.db.impl

import hernanbosqued.backend.use_case.db.DbUseCase
import hernanbosqued.domain.DbController
import hernanbosqued.domain.IdTask
import hernanbosqued.domain.Priority
import hernanbosqued.domain.Task

class DbUseCaseImpl(
    private val dbController: DbController,
) : DbUseCase {
    override fun allTasks(): List<IdTask> {
        return dbController.allTasks()
    }

    override fun addTask(task: Task) {
        dbController.addTask(task)
    }

    override fun removeTask(id: Long): Boolean {
        return dbController.removeTask(id)
    }

    override fun tasksByPriority(priority: Priority): List<IdTask> {
        return dbController.tasksByPriority(priority)
    }

    override fun taskById(taskId: Long): IdTask? {
        return dbController.taskById(taskId)
    }
}
