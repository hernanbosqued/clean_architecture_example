package hernanbosqued.frontend.viewmodel.auth

import hernanbosqued.domain.UserData
import kotlinx.coroutines.flow.StateFlow

interface AuthViewModel {
    val userData: StateFlow<UserData?>

    suspend fun login()

    suspend fun logout()

    fun getButtonText(): String

    suspend fun getButtonFunction()
}
