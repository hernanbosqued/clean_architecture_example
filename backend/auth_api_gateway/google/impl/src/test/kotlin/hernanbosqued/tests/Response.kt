package hernanbosqued.tests

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json.Default.decodeFromJsonElement
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer

@Serializable
data class ServerSuccess<T>(val message: String, val content: T)

@Serializable
data class UserProfile(val userId: String, val displayName: String)


@Serializable(with = ResponseSerializer::class)
sealed interface Response {
    val message: String

    @Serializable
    data class Success(
        override val message: String,
        val content: JsonElement
    ) : Response {
        inline fun <reified T> decode(): T? = decodeFromJsonElement(serializer<T?>(), this.content)
    }


    @Serializable
    data class Error(
        override val message: String,
        val code: Int
    ) : Response
}

sealed interface DomainResponse{
    val message: String

    data class Success<T>(
        override val message: String,
        val content: T?
    ) : DomainResponse

    data class Error(
        override val message: String,
        val code: Int
    ) : DomainResponse
}