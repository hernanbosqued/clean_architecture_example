package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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

        LaunchedEffect(Unit) {
            tasks = repository.allTasks()
        }

        MaterialTheme {
            TaskList(tasks = tasks)
        }
    }
}

@Composable
fun TaskList(tasks: List<DTOIdTask>) {
    Column {
        tasks.forEach { task ->
            TaskItem(task = task)
        }
    }
}


@Composable
fun TaskItem(task: DTOIdTask) {
    Column {
        Text(
            text = "Task #${task.id}: ${task.name}",
            style = MaterialTheme.typography.h6
        )
        Text(
            text = task.description,
            style = MaterialTheme.typography.body1
        )
        Text(
            text = "Priority: ${task.priority}",
            style = MaterialTheme.typography.caption
        )
    }
}
