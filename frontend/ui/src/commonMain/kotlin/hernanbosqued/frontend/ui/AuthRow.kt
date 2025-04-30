package hernanbosqued.frontend.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hernanbosqued.frontend.ui.viewModels.AuthViewModel
import org.koin.compose.koinInject

@Preview
@Composable
fun AuthScreen(viewModel: AuthViewModel = koinInject<AuthViewModel>()) {
    DisposableEffect(viewModel) {
        onDispose {
            viewModel.clean()
        }
    }

    val padding = 6.dp
    val authState by viewModel.authState.collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth().background(Color.LightGray),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(padding),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (authState.isLoggedIn) {
                Text(
                    modifier = Modifier.padding(padding),
                    text = "Bienvenido!",
                )
            }
            Button(onClick = if (authState.isLoggedIn) viewModel::logout else viewModel::login) {
                if (authState.isLoggedIn) {
                    Text("Logout")
                } else {
                    Text("Login with Atlanta")
                }
            }
        }
    }
}
