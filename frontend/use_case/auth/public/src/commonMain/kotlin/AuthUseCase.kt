package hernanbosqued.frontend.usecase.auth

import hernanbosqued.domain.AuthData
import hernanbosqued.domain.UserData
import kotlinx.coroutines.flow.StateFlow

interface AuthUseCase {
    val userData: StateFlow<UserData?>

    suspend fun login(authCode: String? = null)

    suspend fun getUserDataFromAuthCode(authCode: String)

    suspend fun logout()

    fun provideTokens(): AuthData?

    suspend fun refreshTokens(): AuthData?
}
