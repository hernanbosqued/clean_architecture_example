package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import hernanbosqued.domain.IdTask
import hernanbosqued.frontend.viewmodel.task.TaskViewModel
import kotlinx.coroutines.launch
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App(fromUrl: String? = null) {
    KoinContext {
        val viewModel = koinInject<TaskViewModel>()
        var tasks by remember { mutableStateOf<List<IdTask>>(emptyList()) }
        var isLogged by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()

        MaterialTheme {
            Column {
                AuthRow(fromUrl) { isLogged = it }
                if (isLogged) {
                    coroutineScope.launch {
                        tasks = viewModel.getTasks()
                    }
                    TaskList(tasks = tasks)
                } else {
                    tasks = emptyList()
                }
            }
        }
    }
}
