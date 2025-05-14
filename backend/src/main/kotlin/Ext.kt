package hernanbosqued.backend

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.JwkProviderBuilder
import hernanbosqued.backend.presenter.StatusCode
import hernanbosqued.constants.Constants
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import java.net.URL
import java.util.concurrent.TimeUnit

val jwkProvider: JwkProvider =
    JwkProviderBuilder(URL(Constants.GOOGLE_JWKS))
        .cached(1, 5, TimeUnit.MINUTES)
        .rateLimited(10, 10, TimeUnit.SECONDS)
        .build()

fun StatusCode.map(): HttpStatusCode {
    return when (this) {
        StatusCode.BadRequest -> HttpStatusCode.BadRequest
        StatusCode.NotFound -> HttpStatusCode.NotFound
    }
}

suspend inline fun RoutingCall.withUserId(block:(String) -> Unit) {
    val principal = principal<JWTPrincipal>()
    val userId = principal?.payload?.getClaim("sub")?.asString()
    userId?.let {
        block(it)
    } ?: run {
        respond(HttpStatusCode.BadRequest, "User identifier (sub claim) missing or invalid in token")
    }
}