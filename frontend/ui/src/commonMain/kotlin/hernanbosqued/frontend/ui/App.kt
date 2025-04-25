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
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.core.context.startKoin

@Composable
@Preview
fun App() {
    startKoin {
        modules(RepositoryModule.getModule(BuildKonfig.apiUrl)) }

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
