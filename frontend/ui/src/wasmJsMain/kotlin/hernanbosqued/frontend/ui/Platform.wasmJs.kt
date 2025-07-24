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
import io.ktor.http.Url
import kotlinx.coroutines.launch
import org.jetbrains.skia.Image

class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

fun getAuthCodeFromQuerystring(querystring: String): String {
    val fullUrlString = "http://dummy.com$querystring"
    val url = Url(fullUrlString)
    val params = url.parameters
    return requireNotNull(params["code"])
}

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
    val decodedString = atob(encoded)
    val byteArray = ByteArray(decodedString.length)
    for (i in decodedString.indices) {
        byteArray[i] = decodedString[i].code.toByte()
    }
    return byteArray
}

actual fun base64EncodedImageBitmap(totpUri: String): ImageBitmap {
    val pureBase64 = if (totpUri.contains(",")) {
        totpUri.substringAfter(",")
    } else {
        totpUri
    }

    val decodedBytes = base64Decode(pureBase64)
    return Image.makeFromEncoded(decodedBytes).toComposeImageBitmap()
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

private external fun atob(encoded: String): String