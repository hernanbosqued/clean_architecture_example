package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.Dp
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

@Composable
actual fun base64EncodedImageBitmap(totpUri: String): ImageBitmap {
    return remember(totpUri) {
        val pureBase64 = if (totpUri.contains(",")) {
            totpUri.substringAfter(",")
        } else {
            totpUri
        }

        val bytes = base64Decode(pureBase64)
        Image.makeFromEncoded(bytes).toComposeImageBitmap()
    }
}

@Composable
actual fun getFido2LoginButton(viewModel: AuthViewModel, padding: Dp) {
    //Not implemented for this platform
}