package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hernanbosqued.backend.domain.Priority

@Composable
fun PrioritySelector(
    initialPriority: Priority,
    onPrioritySelected: (Priority) -> Unit,
) {
    var priority by rememberSaveable { mutableStateOf(initialPriority) }

    Card(
        modifier = Modifier.Companion.fillMaxWidth(0.5f).padding(16.dp),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.Companion.padding(16.dp).fillMaxWidth(),
        ) {
            PriorityDropdown(
                selectedPriority = priority,
                onPrioritySelected = { priority = it },
            )
            Button(
                onClick = {
                    onPrioritySelected(priority)
                },
            ) {
                Text("Go")
            }
        }
    }
}
