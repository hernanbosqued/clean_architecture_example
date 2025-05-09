package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hernanbosqued.domain.IdTask
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun TaskItem(task: IdTask, onDeleteClick: (Long) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(8.dp),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colors.surface,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = task.name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.weight(1f),
                    )
                    PriorityChip(priority = task.priority)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = task.description,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp),
                )
            }
        }
        IconButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = { onDeleteClick(task.id) }
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete Task",
                tint = MaterialTheme.colors.error
            )
        }
    }
}
