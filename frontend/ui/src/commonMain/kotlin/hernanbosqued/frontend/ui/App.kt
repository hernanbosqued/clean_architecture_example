package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import hernanbosqued.domain.FrontendRepository
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import hernanbosqued.frontend.viewmodel.auth.LoginActions
import io.ktor.http.Parameters
import io.ktor.http.URLBuilder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App() {
    KoinContext {
        val authViewModel = koinInject<AuthViewModel>()
        val authState by authViewModel.authState.collectAsState()

        MaterialTheme {
            Column {
                AuthRow()
                if (authState != null) {
                    TaskList()
                }
            }
        }
    }
}

abstract class LoginActionsBase(
    val clientId: String,
    val redirectUri: String,
    val scopes: List<String>,
    val repository: FrontendRepository
) : LoginActions {

    override val userData: MutableSharedFlow<UserData?> = MutableSharedFlow(replay = 1)

    suspend fun sendAuthCode(authCode: String): UserData {
        return repository.sendAuthorizationCode(authCode, clientId, redirectUri)
    }

    fun generateAuthorizationUrl(
        clientId: String,
        redirectUri: String,
        scopes: List<String>,
    ): String {
        val parameters =
            Parameters.build {
                append("client_id", clientId)
                append("redirect_uri", redirectUri)
                append("prompt", "consent")
                append("access_type", "offline")
                append("scope", scopes.joinToString(" "))
                append("response_type", "code")
            }

        val urlBuilder = URLBuilder("https://accounts.google.com/o/oauth2/v2/auth")
        urlBuilder.parameters.appendAll(parameters)
        return urlBuilder.buildString()
    }
}