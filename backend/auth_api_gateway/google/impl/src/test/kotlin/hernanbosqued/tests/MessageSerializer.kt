package hernanbosqued.tests

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

object MessageSerializer : JsonContentPolymorphicSerializer<Response>(Response::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Response> {
        val jsonObject = element.jsonObject
        return when {
            "content" in jsonObject -> Response.Success.serializer()
            "code" in jsonObject -> Response.Error.serializer()
            else -> throw IllegalArgumentException("Unknown message type: $element")
        }
    }
}