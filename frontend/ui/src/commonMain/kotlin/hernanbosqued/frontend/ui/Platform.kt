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

@Composable
expect fun base64EncodedImageBitmap(qrBase64: String): ImageBitmap

expect fun base64Decode(encoded: String): ByteArray