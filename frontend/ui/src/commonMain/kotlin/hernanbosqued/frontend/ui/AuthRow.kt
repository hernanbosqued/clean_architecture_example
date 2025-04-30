package hernanbosqued.frontend.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import hernanbosqued.frontend.ui.viewModels.AuthViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun AuthScreen(
    fromUrl: String?,
    isLogged: (Boolean) -> Unit
) {
    val viewModel: AuthViewModel = koinInject<AuthViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val authState by viewModel.authState.collectAsState()
    var url by remember { mutableStateOf(fromUrl) }

    LaunchedEffect(authState) {
        isLogged(authState != null)
    }

    println("FROM_URL ->$url")

    if (authState == null && url.isNullOrBlank().not()) {
        coroutineScope.launch { viewModel.processUrl(url!!) }
    }

    val padding = 6.dp
    val rowSize = 60.dp

    Column(
        modifier = Modifier.fillMaxWidth().size(rowSize).background(Color.LightGray),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(padding),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (authState != null) {
                Text(
                    modifier = Modifier.padding(padding),
                    text = authState!!.name,
                )

                AsyncImage(
                    modifier = Modifier.padding(padding).clip(CircleShape),
                    model = authState!!.pictureUrl,
                    contentDescription = null,
                )
            }

            Button(
                modifier = Modifier.padding(padding),
                onClick = {
                    coroutineScope.launch {
                        if (authState != null) {
                            viewModel.logout()
                            url = null
                        } else {
                            viewModel.login()
                        }
                    }
                },
            ) {
                if (authState != null) {
                    Text("Logout")
                } else {
                    Text("Login with Google")
                }
            }
        }
    }
}
