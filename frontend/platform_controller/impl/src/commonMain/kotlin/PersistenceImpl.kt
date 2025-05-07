import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import hernanbosqued.domain.Persistence
import hernanbosqued.domain.UserData
import hernanbosqued.domain.dto.DTOUserData
import kotlinx.serialization.json.Json

class PersistenceImpl(
    private val settings: Settings,
    private val json: Json,
) : Persistence {
    companion object {
        private const val KEY_USER_DATA = "auth_user_data"
    }

    override fun saveUserData(user: UserData) {
        val loadedUserData = loadUserData()
        println("LOADED --------> $loadedUserData")
        try {
            val serializableUserData =
                DTOUserData(
                    name = user.name ?: loadedUserData?.name,
                    email = user.email,
                    pictureUrl = user.pictureUrl ?: loadedUserData?.pictureUrl,
                    accessToken = user.accessToken,
                    refreshToken = user.refreshToken ?: loadedUserData?.refreshToken,
                )

            val userJson = json.encodeToString(serializableUserData)

            println("SAVED  --------> $serializableUserData")

            settings.set(
                KEY_USER_DATA,
                value = userJson,
            )
        } catch (e: Exception) {
            println("Error serializando UserData: ${e.message}")
            settings.remove(KEY_USER_DATA)
        }
    }

    override fun loadUserData(): UserData? {
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

    override fun clearUserData() {
        settings.remove(KEY_USER_DATA)
    }
}
