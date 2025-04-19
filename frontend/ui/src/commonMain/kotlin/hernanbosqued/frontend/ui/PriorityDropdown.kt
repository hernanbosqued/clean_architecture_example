package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hernanbosqued.backend.domain.Priority

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PriorityDropdown(
    selectedPriority: Priority, onPrioritySelected: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { expanded = it }, modifier = Modifier.Companion.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = selectedPriority.toString(),
            onValueChange = {},
            readOnly = true,
            label = { Text("Priority") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.Companion.fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {
            Priority.entries.forEach { priority ->
                DropdownMenuItem(
                    onClick = {
                        onPrioritySelected(priority)
                        expanded = false
                    }) {
                    Text(priority.toString())
                }
            }
        }
    }
}