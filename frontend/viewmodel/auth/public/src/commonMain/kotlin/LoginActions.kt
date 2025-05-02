package hernanbosqued.frontend.viewmodel.auth

import hernanbosqued.domain.UserData
import kotlinx.coroutines.flow.SharedFlow

interface LoginActions {
    val userData: SharedFlow<UserData?>
    suspend fun login()
}