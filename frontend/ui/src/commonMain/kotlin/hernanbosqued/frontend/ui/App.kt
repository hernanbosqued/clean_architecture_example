package hernanbosqued.frontend.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
fun App() {
    KoinContext {
        MaterialTheme {
            Column {
                val authViewModel = koinInject<AuthViewModel>()
                val userData by authViewModel.userData.collectAsState()

                println("---------------AUTHSTATE----------$userData")

                AuthRow()
                if (userData != null) {
                    println("---------------SE COMPONE TASKLIST----------")
                    TaskList()
                }
            }
        }
    }
}
