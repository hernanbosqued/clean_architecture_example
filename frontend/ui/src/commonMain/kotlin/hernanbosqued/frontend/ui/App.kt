package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import hernanbosqued.backend.presenter.DTOIdTask
import hernanbosqued.frontend.repository.Repository
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App() {
    KoinContext {
        val repository: Repository = koinInject<Repository>()
        var tasks by remember { mutableStateOf<List<DTOIdTask>>(emptyList()) }

        MaterialTheme {
            Column {
                AuthScreen()
//                RepositoryActions(repository = repository, onTasksUpdated = { tasks = it })
//                TaskList(tasks = tasks)
            }
        }
    }
}
