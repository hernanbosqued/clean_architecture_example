package hernanbosqued.domain

import kotlinx.coroutines.flow.StateFlow

interface Persistence {
    val userData: StateFlow<UserData?>

    suspend fun saveUserData(user: UserData)

    suspend fun clearUserData()
}
