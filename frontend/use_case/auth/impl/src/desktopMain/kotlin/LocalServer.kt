package hernanbosqued.frontend.usecase.auth.impl

import io.ktor.http.ContentType
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.CompletableDeferred

class LocalServer(port: Int) {
    private val codeDeferred = CompletableDeferred<String?>()

    private val server =
        embeddedServer(factory = Netty, port = port) {
            routing {
                get("/") {
                    codeDeferred.complete(call.request.queryParameters["code"])
                    call.respondText(
                        """
                        <html><body><h1>Autenticación Exitosa</h1><p>Puedes cerrar esta ventana.</p><script>window.close();</script></body></html>
                    """,
                        ContentType.Text.Html,
                    )
                }
            }
        }.start(wait = false)

    suspend fun waitForCode(): String? = codeDeferred.await().also { server.stop() }
}
