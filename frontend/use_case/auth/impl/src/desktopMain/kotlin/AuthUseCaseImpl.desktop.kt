package hernanbosqued.frontend.usecase.auth.impl

import java.awt.Desktop
import java.net.URI

fun openWebPage(url: String) {
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        try {
            Desktop.getDesktop().browse(URI(url))
        } catch (e: Exception) {
            println("Error al abrir el navegador: ${e.message}")
            // Manejar el error al abrir el navegador
        }
    } else {
        println("La funcionalidad de navegación no está soportada en este sistema.")
        // Manejar el caso donde la navegación no está soportada
    }
}
