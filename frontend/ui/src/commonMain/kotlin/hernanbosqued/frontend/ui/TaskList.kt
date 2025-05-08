package hernanbosqued.frontend.ui

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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

//@Composable
//fun TaskList() {
//    val viewModel = koinInject<TaskViewModel>()
//    val tasks by viewModel.tasks.collectAsState()
//    val scrollState = rememberScrollState()
//
//    Box(
//        modifier = Modifier.Companion.fillMaxSize(),
//    ) {
//        Column(
//            modifier = Modifier.Companion.verticalScroll(scrollState).fillMaxWidth(),
//        ) {
//            tasks.forEach { task ->
//                TaskItem(task = task)
//            }
//        }
//        VerticalScrollbar(
//            modifier = Modifier.Companion.align(Alignment.Companion.CenterEnd),
//            adapter = rememberScrollbarAdapter(scrollState),
//        )
//    }
//}

@Composable
fun TaskList(
) {
    val viewModel = koinInject<TaskViewModel>()
    val tasks by viewModel.tasks.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task")
            }
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(tasks.size) { task ->
                TaskItem(task = tasks[task])
            }
        }

        if (showDialog) {
            AddTaskDialog(
                onDismissRequest = { showDialog = false },
                onSaveTask = { name, description, priority ->
                    coroutineScope.launch { viewModel.addTask(name, description, priority) }
                    showDialog = false
                }
            )
        }
    }
}