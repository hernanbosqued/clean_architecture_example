package hernanbosqued.backend.presenter

sealed class StatusCode {
    data object NotFound : StatusCode()

    data object BadRequest : StatusCode()
}
