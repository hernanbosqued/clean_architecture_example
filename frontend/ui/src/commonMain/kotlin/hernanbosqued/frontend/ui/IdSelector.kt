package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun IdSelector(onIdSelected: (Int) -> Unit) {
    var id by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.Companion.fillMaxWidth(0.5f).padding(16.dp),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.Companion.padding(16.dp).fillMaxWidth(),
        ) {
            OutlinedTextField(
                value = id,
                onValueChange = { newValue ->
                    // Solo acepta dígitos
                    if (newValue.all { it.isDigit() }) {
                        id = newValue
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Task Id") },
                modifier =
                    Modifier.fillMaxWidth().padding(
                        vertical = 8.dp,
                    ),
            )
            Button(
                onClick = {
                    onIdSelected(id.toInt())
                },
            ) {
                Text("Go")
            }
        }
    }
}
