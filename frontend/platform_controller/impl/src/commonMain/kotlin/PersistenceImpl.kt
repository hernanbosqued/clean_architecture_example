package hernanbosqued.frontend.platform_controller.impl

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import hernanbosqued.domain.Persistence
import hernanbosqued.domain.UserData
import hernanbosqued.domain.dto.DTOUserData
import hernanbosqued.domain.dto.toDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json

class PersistenceImpl(
    private val settings: Settings,
    private val json: Json,
) : Persistence {
    companion object {
        private const val KEY_USER_DATA = "auth_user_data"
    }

    private val _userData = MutableStateFlow(loadUserData())
    override val userData: StateFlow<UserData?> = _userData

    override suspend fun saveUserData(user: UserData) {
        try {
            val userJson = json.encodeToString(user.toDto())

            settings.set(
                key = KEY_USER_DATA,
                value = userJson,
            )

            _userData.emit(user)
        } catch (e: Exception) {
            println("Error serializando UserData: ${e.message}")
            settings.remove(KEY_USER_DATA)
        }
    }

    private fun loadUserData(): UserData? {
        val userJson: String? = settings[KEY_USER_DATA]

        return if (userJson.isNullOrBlank()) {
            null
        } else {
            try {
                json.decodeFromString<DTOUserData>(userJson)
            } catch (e: Exception) {
                println("Error deserializando UserData: ${e.message}")
                settings.remove(KEY_USER_DATA)
                null
            }
        }
    }

    override suspend fun clearUserData() {
        settings.remove(KEY_USER_DATA)
        _userData.emit(null)
    }
}
