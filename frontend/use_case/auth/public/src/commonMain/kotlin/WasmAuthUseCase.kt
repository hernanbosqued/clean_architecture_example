package hernanbosqued.frontend.usecase.auth

interface WasmAuthUseCase : AuthUseCase {
    suspend fun setUserDataFromUrl(urlString: String)
}
