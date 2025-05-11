package hernanbosqued.frontend.viewmodel.auth

import hernanbosqued.domain.UserData
import kotlinx.coroutines.flow.StateFlow

interface AuthViewModel {
    val userData: StateFlow<UserData?>

    suspend fun login(authCode: String? = null)

    suspend fun logout()
}
