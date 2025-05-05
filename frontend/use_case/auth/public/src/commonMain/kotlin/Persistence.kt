package hernanbosqued.frontend.usecase.auth

import hernanbosqued.domain.UserData

interface Persistence {
    fun saveUserData(user: UserData)

    fun loadUserData(): UserData?

    fun clearUserData()
}
