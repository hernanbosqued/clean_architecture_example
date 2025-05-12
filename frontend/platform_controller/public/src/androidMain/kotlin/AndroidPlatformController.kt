package hernanbosqued.frontend.platform_controller.di

import android.content.Intent
import androidx.activity.result.ActivityResult

interface AndroidPlatformController {
    fun provideIntent(): Intent
    fun handleResult(result: ActivityResult, function: (String) -> Unit)
    fun signOut()

}