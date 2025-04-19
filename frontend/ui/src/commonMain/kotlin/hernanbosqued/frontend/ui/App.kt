package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import hernanbosqued.backend.presenter.DTOIdTask
import hernanbosqued.frontend.repository.Repository
import hernanbosqued.frontend.repository.di.RepositoryModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.core.context.startKoin

@Composable
@Preview
fun App() {
    startKoin { modules(RepositoryModule.getModule()) }

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