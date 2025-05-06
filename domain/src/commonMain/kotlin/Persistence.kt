package hernanbosqued.domain

interface Persistence {
    fun saveUserData(user: UserData)

    fun loadUserData(): UserData?

    fun clearUserData()
}