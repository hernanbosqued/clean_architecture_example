package hernanbosqued.backend.service

import hernanbosqued.backend.domain.IdTask
import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.domain.Repository
import hernanbosqued.backend.domain.Task
import hernanbosqued.backend.repo.RepositoryModule
import hernanbosqued.backend.service.public.Service
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module(includes = [RepositoryModule::class])
@ComponentScan
class ServiceModule

@Factory
class ServiceImpl(
    private val repository: Repository
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