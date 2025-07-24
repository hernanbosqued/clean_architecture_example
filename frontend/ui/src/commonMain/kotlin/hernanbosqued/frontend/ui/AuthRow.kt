package hernanbosqued.frontend.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import org.koin.compose.koinInject

@Composable
fun AuthRow() {
    val viewModel: AuthViewModel = koinInject<AuthViewModel>()
    val userData by viewModel.userData.collectAsState()
    val padding = 6.dp
    val rowSize = 60.dp

    val buttonText: String = if (userData != null) "Logout" else "Login with Google"

    Row(
        modifier = Modifier.fillMaxWidth().size(rowSize).background(Color.LightGray).padding(padding),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        userData?.let {
            Text(
                modifier = Modifier.padding(padding),
                text = it.name,
            )

            AsyncImage(
                modifier = Modifier.padding(padding).clip(CircleShape),
                model = it.pictureUrl,
                contentDescription = null,
            )
        }

        getLoginButton(buttonText, userData, viewModel, padding)
    }
}