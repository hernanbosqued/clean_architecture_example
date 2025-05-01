package hernanbosqued.backend.presenter

sealed class Result<S, E> {
    data class Success<S, E>(val value: S) : Result<S, E>()

    data class Error<S, E>(val error: E) : Result<S, E>()
}
