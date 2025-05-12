package hernanbosqued.frontend.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.platform_controller.di.AndroidPlatformController
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


@Composable
actual fun getLoginButton(text: String, userData: UserData?, viewModel: AuthViewModel, padding: Dp) {
    val platformController = koinInject<AndroidPlatformController>()
    val coroutineScope = rememberCoroutineScope()

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            platformController.handleResult(result) { authCode: String ->
                coroutineScope.launch { viewModel.login(authCode) }
            }
        })

    Button(
        modifier = Modifier.padding(padding),
        onClick = {
            if (userData == null) {
                googleSignInLauncher.launch(platformController.provideIntent())
            } else {
                coroutineScope.launch {
                    platformController.signOut()
                    viewModel.logout()
                }
            }
        },
    ) {
        Text(text)
    }
}
