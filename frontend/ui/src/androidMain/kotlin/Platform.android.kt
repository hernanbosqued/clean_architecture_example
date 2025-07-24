package hernanbosqued.frontend.ui

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import hernanbosqued.constants.Constants
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import kotlinx.coroutines.launch
import androidx.core.net.toUri

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun base64EncodedImageBitmap(totpUri: String): ImageBitmap {
    TODO("Not implementation needed")
}

actual fun base64Decode(encoded: String): ByteArray {
    TODO("Not implementation needed")
}

@Composable
actual fun getLoginButton(text: String, userData: UserData?, viewModel: AuthViewModel, padding: Dp) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestServerAuthCode(Constants.GOOGLE_CLIENT, true)
        .requestEmail()
        .requestProfile()
        .build()

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.getResult(ApiException::class.java)
            coroutineScope.launch {
                viewModel.login(account.serverAuthCode)
            }
        }
    }

    Button(
        modifier = Modifier.padding(padding),
        onClick = {
            if (userData == null) {
                googleSignInLauncher.launch(googleSignInClient.signInIntent)
            } else {
                coroutineScope.launch {
                    googleSignInClient.signOut()
                    viewModel.logout()
                }
            }
        },
    ) {
        Text(text)
    }
}

@Composable
actual fun manageAuthenticator(viewModel: AuthViewModel, userData: UserData, padding: Dp) {
    val context = LocalContext.current

    Button(
        modifier = Modifier.padding(padding),
        onClick = {
            openActivity(context, userData.totpUri.toUri()) {
                val authenticatorPackageName = "com.google.android.apps.authenticator2"
                val uri = "market://details?id=$authenticatorPackageName".toUri()
                openActivity(context, uri) {}
            }
        }
    ) {
        Text("Configure Authenticator")
    }
}

fun openActivity(context: Context, uri: Uri, onError: () -> Unit) {
    try {
        val intent = Intent(
            /* action = */ Intent.ACTION_VIEW,
            /* uri = */ uri
        ).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        onError()
    }
}