package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IdSelector(
    onIdSelected: (Int) -> Unit,
) {
    var id by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.Companion.fillMaxWidth(0.5f).padding(16.dp), elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.Companion.padding(16.dp).fillMaxWidth()
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
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp
                )
            )
            Button(
                onClick = {
                    onIdSelected(id.toInt())
                }) {
                Text("Go")
            }
        }
    }
}