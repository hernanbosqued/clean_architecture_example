package hernanbosqued.backend

import hernanbosqued.backend.presenter.Presenter
import hernanbosqued.domain.dto.DTOAuthCodeRequest
import hernanbosqued.domain.dto.DTOAuthRefreshTokenRequest
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Routing.addAuthRouting(presenter: Presenter) = route("/auth") {
    post("/code") {
        val code = call.receive<DTOAuthCodeRequest>()
        val userData = presenter.getUserData(code)
        call.respond(userData)
    }

    post("/refresh_token") {
        val code = call.receive<DTOAuthRefreshTokenRequest>()
        val response = presenter.refreshToken(code)
        call.respond(response)
    }
}