package hernanbosqued.frontend.platform_controller.di

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import hernanbosqued.constants.Constants

class AndroidPlatformControllerImpl(
    context: Context
) : AndroidPlatformController {

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestServerAuthCode(Constants.GOOGLE_CLIENT, true)
        .requestEmail()
        .requestProfile()
        .build()

    val googleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(context, gso)

    override fun provideIntent(): Intent = googleSignInClient.signInIntent

    override fun handleResult(result: ActivityResult, function: (String) -> Unit) {
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.getResult(ApiException::class.java)
            function(requireNotNull(account.serverAuthCode))
        }
    }

    override fun signOut() {
        googleSignInClient.signOut()
    }
}


