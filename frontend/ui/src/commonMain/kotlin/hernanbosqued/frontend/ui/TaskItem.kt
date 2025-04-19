package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hernanbosqued.backend.presenter.DTOIdTask
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun TaskItem(task: DTOIdTask) {
    Card(
        modifier = Modifier.Companion.fillMaxWidth(0.5f).padding(8.dp), elevation = 4.dp, backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.Companion.padding(16.dp).fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.Companion.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Companion.CenterVertically
            ) {
                Text(
                    text = task.name, style = MaterialTheme.typography.h6, modifier = Modifier.Companion.weight(1f)
                )
                PriorityChip(priority = task.priority)
            }

            Spacer(modifier = Modifier.Companion.height(8.dp))

            Text(
                text = task.description, style = MaterialTheme.typography.body1, modifier = Modifier.Companion.padding(vertical = 4.dp)
            )

            Divider(
                modifier = Modifier.Companion.padding(vertical = 8.dp), color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
            )

            Text(
                text = "ID: ${task.id}", style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}