package hernanbosqued.frontend.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.launch
import org.jetbrains.skia.Image
import java.util.Base64

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

@Composable
actual fun getLoginButton(text: String, userData: UserData?, viewModel: AuthViewModel, padding: Dp) {
    val coroutineScope = rememberCoroutineScope()
    Button(
        modifier = Modifier.padding(padding),
        onClick = {
            coroutineScope.launch {
                if (userData == null) {
                    viewModel.login()
                } else {
                    viewModel.logout()
                }
            }
        },
    ) {
        Text(text)
    }
}

actual fun base64Decode(encoded: String): ByteArray {
    return Base64.getDecoder().decode(encoded)
}

actual fun base64EncodedImageBitmap(totpUri: String): ImageBitmap {
        val pureBase64 = if (totpUri.contains(",")) {
            totpUri.substringAfter(",")
        } else {
            totpUri
        }

        val bytes = base64Decode(pureBase64)
        return Image.makeFromEncoded(bytes).toComposeImageBitmap()
}

@Composable
actual fun manageAuthenticator(viewModel: AuthViewModel, userData: UserData, padding: Dp) {
    val imageBitmap = remember { base64EncodedImageBitmap(userData.totpUriQrCode) }

    Image(
        bitmap = imageBitmap,
        contentDescription = "Código QR para 2FA",
        modifier = Modifier.size(200.dp)
    )
}