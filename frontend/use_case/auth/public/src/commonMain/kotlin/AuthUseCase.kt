package hernanbosqued.frontend.usecase.auth

import hernanbosqued.domain.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AuthUseCase {
    val userData: StateFlow<UserData?>

    suspend fun login()

    suspend fun getUserDataFromAuthCode(authCode: String)

    suspend fun logout()
}
