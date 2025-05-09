package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import hernanbosqued.frontend.viewmodel.task.TaskViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun TaskList(
) {
    val viewModel = koinInject<TaskViewModel>()
    val tasksState by viewModel.tasksState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add Task")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(tasksState.tasks.size) { index ->
                    TaskItem(task = tasksState.tasks[index], { taskId -> coroutineScope.launch { viewModel.removeTask(taskId) } })
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

            if (tasksState.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}