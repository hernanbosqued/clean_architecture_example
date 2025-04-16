package hernanbosqued.backend.use_cases

import hernanbosqued.backend.entities.IdTask
import hernanbosqued.backend.entities.Priority
import hernanbosqued.backend.entities.Repository
import hernanbosqued.backend.entities.Task

class UseCase(
    private val repository: Repository
) {
    fun allTasks(): List<IdTask> {
        return repository.allTasks()
    }

    fun addTask(task: Task) {
        repository.addTask(task)
    }

    fun removeTask(id: Int): Boolean {
        return repository.removeTask(id)
    }

    fun tasksByPriority(priority: Priority): List<IdTask> {
        return repository.tasksByPriority(priority)
    }

    fun taskById(taskId: Int): IdTask? {
        return repository.taskById(taskId)
    }
}