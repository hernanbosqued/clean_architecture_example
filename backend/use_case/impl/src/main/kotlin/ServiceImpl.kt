package hernanbosqued.backend.service.impl

import hernanbosqued.backend.domain.Controller
import hernanbosqued.backend.domain.IdTask
import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.domain.Task
import hernanbosqued.backend.service.public.Service

class ServiceImpl(
    private val repository: Controller,
) : Service {
    override fun allTasks(): List<IdTask> {
        return repository.allTasks()
    }

    override fun addTask(task: Task) {
        repository.addTask(task)
    }

    override fun removeTask(id: Int): Boolean {
        return repository.removeTask(id)
    }

    override fun tasksByPriority(priority: Priority): List<IdTask> {
        return repository.tasksByPriority(priority)
    }

    override fun taskById(taskId: Int): IdTask? {
        return repository.taskById(taskId)
    }
}
