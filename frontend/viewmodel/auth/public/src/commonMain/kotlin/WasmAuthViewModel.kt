package hernanbosqued.frontend.viewmodel.auth

interface WasmAuthViewModel : AuthViewModel {
    suspend fun setUserData(authCode: String)
}
