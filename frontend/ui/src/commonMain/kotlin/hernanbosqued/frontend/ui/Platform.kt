package hernanbosqued.frontend.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@Composable
expect fun getLoginButton(text: String, userData: UserData?, viewModel: AuthViewModel, padding: Dp)

expect fun base64EncodedImageBitmap(totpUri: String): ImageBitmap

expect fun base64Decode(encoded: String): ByteArray

@Composable
expect fun manageAuthenticator(viewModel: AuthViewModel, userData: UserData, padding: Dp)