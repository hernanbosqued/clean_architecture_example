package hernanbosqued.frontend.usecase.auth

import hernanbosqued.domain.UserData
import kotlinx.coroutines.flow.SharedFlow

interface AuthUseCase {
    val userData: SharedFlow<UserData?>

    suspend fun init()

    suspend fun login()

    suspend fun getUserDataFromAuthCode(authCode: String)

    suspend fun logout()
}
