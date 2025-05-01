package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hernanbosqued.backend.presenter.DTOIdTask
import hernanbosqued.domain.Priority
import hernanbosqued.frontend.repository.Repository
import kotlinx.coroutines.launch

enum class IdSelectorAction {
    SELECT,
    REMOVE,
}

@Composable
fun RepositoryActions(
    repository: Repository,
    onTasksUpdated: (List<DTOIdTask>) -> Unit,
) {
    val scope = rememberCoroutineScope()
    var showAddTaskForm by remember { mutableStateOf(false) }
    var showPrioritySelector by remember { mutableStateOf(false) }
    var showIdSelector by remember { mutableStateOf(false) }
    var selectedPriority by remember { mutableStateOf(Priority.Low) }
    var idSelectorAction by remember { mutableStateOf(IdSelectorAction.SELECT) }

    Column {
        Row(
            modifier = Modifier.Companion.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(
                onClick = {
                    scope.launch {
                        onTasksUpdated(repository.allTasks())
                        showAddTaskForm = false
                    }
                },
            ) {
                Text("Get All Tasks")
            }

            Spacer(modifier = Modifier.Companion.width(8.dp))

            Button(
                onClick = {
                    idSelectorAction = IdSelectorAction.SELECT
                    showIdSelector = !showIdSelector
                },
            ) {
                Text("Get Task By ID")
            }

            Spacer(modifier = Modifier.Companion.width(8.dp))

            Button(
                onClick = {
                    showPrioritySelector = !showPrioritySelector
                },
            ) {
                Text("Get By Priority")
            }

            Spacer(modifier = Modifier.Companion.width(8.dp))

            Button(
                onClick = {
                    showAddTaskForm = !showAddTaskForm
                },
            ) {
                Text(if (showAddTaskForm) "Hide Form" else "Add Task")
            }

            Spacer(modifier = Modifier.Companion.width(8.dp))

            Button(
                onClick = {
                    idSelectorAction = IdSelectorAction.REMOVE
                    showIdSelector = !showIdSelector
                },
            ) {
                Text("Remove Task")
            }
        }

        if (showAddTaskForm) {
            AddTaskForm(
                repository = repository,
                onTaskAdded = {
                    scope.launch {
                        val updatedTasks = repository.allTasks()
                        onTasksUpdated(updatedTasks)
                        showAddTaskForm = false
                    }
                },
                onDismiss = { showAddTaskForm = false },
            )
        }

        if (showPrioritySelector) {
            PrioritySelector(selectedPriority) {
                showPrioritySelector = false
                scope.launch {
                    selectedPriority = it
                    onTasksUpdated(repository.taskByPriority(it))
                }
            }
        }

        if (showIdSelector) {
            IdSelector {
                scope.launch {
                    when (idSelectorAction) {
                        IdSelectorAction.SELECT -> repository.taskById(it).also { task -> onTasksUpdated(listOf(task)) }
                        IdSelectorAction.REMOVE -> repository.removeTask(it).also { onTasksUpdated(repository.allTasks()) }
                    }
                }
            }
        }
    }
}
