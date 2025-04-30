package hernanbosqued.frontend.ui

// Estado para la autenticación (simplificado, sin foto)
data class AuthState(
    val isLoggedIn: Boolean = false,
    val userName: String? = null,
)
