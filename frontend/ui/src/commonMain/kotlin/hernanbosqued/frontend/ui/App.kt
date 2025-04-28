package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import hernanbosqued.backend.presenter.DTOIdTask
import hernanbosqued.frontend.buildconfig.BuildKonfig
import hernanbosqued.frontend.repository.Repository
import hernanbosqued.frontend.repository.di.RepositoryModule
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.core.context.startKoin
import androidx.compose.desktop.ui.tooling.preview.Preview
import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.presenter.DTOTask


fun provideRepository(): Repository = object : Repository {
    override suspend fun allTasks(): List<DTOIdTask> = listOf(DTOIdTask(1, "Atlanta 2", "Descripcion", Priority.High))

    override suspend fun taskById(taskId: Int): DTOIdTask {
        TODO("Not yet implemented")
    }

    override suspend fun taskByPriority(priority: Priority): List<DTOIdTask> {
        TODO("Not yet implemented")
    }

    override suspend fun addTask(task: DTOTask) {
        TODO("Not yet implemented")
    }

    override suspend fun removeTask(taskId: Int?) {
        TODO("Not yet implemented")
    }

}

@Preview
@Composable
fun PreviewMiComposableAndroid() {
    var tasks by remember { mutableStateOf(listOf(DTOIdTask(1, "Atlanta 2", "Descripcion", Priority.High))) }
    TaskList(tasks = tasks)
}

@Composable
@Preview
fun MyApp() {
    var tasks by remember { mutableStateOf(listOf(DTOIdTask(1, "Atlanta 2", "Descripcion", Priority.High))) }
    val repository = provideRepository()

    Column {
        RepositoryActions(repository = repository, onTasksUpdated = { tasks = it })
        TaskList(tasks = tasks)
    }
}

@Composable
fun App() {
    startKoin {
        modules(RepositoryModule.getModule(BuildKonfig.apiUrl))
    }

    KoinContext {
        val repository: Repository = koinInject<Repository>()
        var tasks by remember { mutableStateOf<List<DTOIdTask>>(emptyList()) }

        MaterialTheme {
            Column {
                RepositoryActions(repository = repository, onTasksUpdated = { tasks = it })
                TaskList(tasks = tasks)
            }
        }
    }
}
