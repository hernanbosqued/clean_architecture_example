package hernanbosqued.frontend.platform_controller

import hernanbosqued.domain.UserData
import kotlinx.coroutines.flow.SharedFlow

interface LoginActions {
    val userData: SharedFlow<UserData?>

    suspend fun login()
}

interface WasmLoginActions : LoginActions {
    suspend fun getUserDataFromUrl(url: String)
}
