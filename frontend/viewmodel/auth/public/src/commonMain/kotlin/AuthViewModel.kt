package hernanbosqued.frontend.viewmodel.auth

import hernanbosqued.domain.UserData
import kotlinx.coroutines.flow.StateFlow

interface AuthViewModel {
    val authState: StateFlow<UserData?>

    suspend fun login()

    suspend fun processUrl(urlString: String)

    fun logout()
}
