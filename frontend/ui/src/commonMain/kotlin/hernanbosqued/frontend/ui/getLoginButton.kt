package hernanbosqued.frontend.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import hernanbosqued.domain.UserData
import hernanbosqued.frontend.viewmodel.auth.AuthViewModel

@Composable
expect fun getLoginButton(text: String, userData: UserData?, viewModel: AuthViewModel, padding: Dp)
