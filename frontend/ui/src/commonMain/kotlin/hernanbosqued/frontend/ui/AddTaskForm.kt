package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hernanbosqued.backend.domain.Priority
import hernanbosqued.backend.presenter.DTOTask
import hernanbosqued.frontend.repository.Repository
import kotlinx.coroutines.launch

@Composable
fun AddTaskForm(
    repository: Repository,
    onTaskAdded: () -> Unit,
    onDismiss: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf(Priority.Low) }
    var isSubmitting by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Card(
        modifier = Modifier.fillMaxWidth(0.5f).padding(16.dp),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
        ) {
            Text(
                text = "Add New Task",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Task Name") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            )

            OutlinedTextField(
                value = description,
                onValueChange = {
                    description = it
                },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            )

            // Usar el nuevo componente PriorityDropdown
            PriorityDropdown(
                selectedPriority = selectedPriority,
                onPrioritySelected = { selectedPriority = it },
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.padding(end = 8.dp),
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        scope.launch {
                            isSubmitting = true
                            try {
                                val newTask =
                                    DTOTask(
                                        name = name,
                                        description = description,
                                        priority = selectedPriority,
                                    )
                                repository.addTask(newTask)
                                onTaskAdded()
                                onDismiss()
                            } finally {
                                isSubmitting = false
                            }
                        }
                    },
                    enabled = name.isNotBlank() && description.isNotBlank() && !isSubmitting,
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colors.onPrimary,
                        )
                    } else {
                        Text("Add Task")
                    }
                }
            }
        }
    }
}
