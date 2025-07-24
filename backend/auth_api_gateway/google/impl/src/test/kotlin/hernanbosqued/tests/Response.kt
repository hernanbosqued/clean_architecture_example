package hernanbosqued.tests

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json.Default.decodeFromJsonElement
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.serializer

@Serializable
data class ServerSuccess<T>(val message: String, val content: T)

@Serializable
data class UserProfile(val userId: String, val displayName: String)


@Serializable(with = MessageSerializer::class)
sealed interface Response {
    val message: String

    @Serializable//(with = SuccessResponseSerializer::class)
    data class Success(
        override val message: String,
        val content: JsonElement
    ) : Response {
        inline fun <reified T> decode(): T = decodeFromJsonElement(serializer<T>(), this.content)
    }


    @Serializable
    data class Error(
        override val message: String,
        val code: Int
    ) : Response
}