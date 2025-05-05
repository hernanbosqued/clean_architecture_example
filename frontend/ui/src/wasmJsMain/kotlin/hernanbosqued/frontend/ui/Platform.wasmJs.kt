package hernanbosqued.frontend.ui

import io.ktor.http.Url

class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

fun getAuthCodeFromQuerystring(querystring: String): String {
    val fullUrlString = "http://dummy.com$querystring"
    val url = Url(fullUrlString)
    val params = url.parameters
    return requireNotNull(params["code"])
}
