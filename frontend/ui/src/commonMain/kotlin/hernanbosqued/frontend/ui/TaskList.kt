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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import hernanbosqued.backend.presenter.DTOIdTask
import hernanbosqued.domain.IdTask
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun TaskList(tasks: List<IdTask>) {
    val scrollState = rememberScrollState()

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
