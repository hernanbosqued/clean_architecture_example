package hernanbosqued.frontend.ui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import hernanbosqued.domain.IdTask
import hernanbosqued.frontend.viewmodel.task.TaskViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun TaskList() {
    val viewModel = koinInject<TaskViewModel>()
    val scrollState = rememberScrollState()
    var tasks by remember { mutableStateOf<List<IdTask>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    coroutineScope.launch {
        tasks = viewModel.getTasks()
    }

    Box(
        modifier = Modifier.Companion.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.Companion.verticalScroll(scrollState).fillMaxWidth(),
        ) {
            tasks.forEach { task ->
                TaskItem(task = task)
            }
        }
        VerticalScrollbar(
            modifier = Modifier.Companion.align(Alignment.Companion.CenterEnd),
            adapter = rememberScrollbarAdapter(scrollState),
        )
    }
}
