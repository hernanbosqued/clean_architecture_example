package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hernanbosqued.backend.domain.Priority

@Composable
fun PriorityChip(priority: Priority) {
    val backgroundColor = when (priority) {
        Priority.High -> Color(0xFFFFEBEE)   // Light Red
        Priority.Medium -> Color(0xFFFFF3E0)  // Light Orange
        Priority.Low -> Color(0xFFE8F5E9)     // Light Green
        Priority.Vital -> Color(0xFF000000)     // Black
    }

    val textColor = when (priority) {
        Priority.High -> Color(0xFFD32F2F)    // Dark Red
        Priority.Medium -> Color(0xFFF57C00)   // Dark Orange
        Priority.Low -> Color(0xFF388E3C)      // Dark Green
        Priority.Vital -> Color(0xFFFFFFFF)  // White
    }

    Surface(
        color = backgroundColor, shape = RoundedCornerShape(16.dp), modifier = Modifier.Companion.padding(start = 8.dp)
    ) {
        Text(
            text = priority.toString(), modifier = Modifier.Companion.padding(horizontal = 12.dp, vertical = 4.dp), style = MaterialTheme.typography.caption, color = textColor
        )
    }
}