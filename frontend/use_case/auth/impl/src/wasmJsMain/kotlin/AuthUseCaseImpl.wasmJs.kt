package hernanbosqued.frontend.usecase.auth.impl

import kotlinx.browser.window

fun openWebPage(url: String) {
    try {
        window.location.href = url
    } catch (e: Exception) {
        println("Error (inesperado) al intentar redirigir: ${e.message}")
    }
}
