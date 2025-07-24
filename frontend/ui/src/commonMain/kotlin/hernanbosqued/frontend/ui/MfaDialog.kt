package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MfaDialog(
    userData: UserData
) {
    var totp by remember { mutableStateOf("") }
    var isMfaAuthenticated: Boolean? by remember { mutableStateOf(null) }
    val viewModel: AuthViewModel = koinInject<AuthViewModel>()
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = { },
        title = { Text("MFA authentication") },
        text = {
            Column {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = totp,
                    label = { Text("6-digit code") },
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() } && newValue.length <= 6) {
                            totp = newValue
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    color = if (isMfaAuthenticated == false) Color.Red else Color.Transparent,
                    text = "Wrong code, try again",
                )
                Spacer(modifier = Modifier.height(8.dp))

                isMfaAuthenticated ?: manageAuthenticator(viewModel, userData, 8.dp)
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    coroutineScope.launch {
                        isMfaAuthenticated = viewModel.sendTotp(totp.toInt())
                    }
                },
                enabled = totp.length == 6,
            ) {
                Text("Send")
            }
        }
    )
}

