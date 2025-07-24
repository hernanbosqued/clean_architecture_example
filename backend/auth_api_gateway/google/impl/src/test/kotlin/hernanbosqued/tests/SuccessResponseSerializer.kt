package hernanbosqued.tests

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonElement

//object SuccessResponseSerializer : KSerializer<Response.Success> {
//    // 1. Describe la estructura de la clase
//    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("SuccessResponse") {
//        element<String>("message")
//        element<String>("content")
//    }
//
//    // 2. Define cómo convertir el objeto a JSON
//    override fun serialize(encoder: Encoder, value: Response.Success) {
//        encoder.encodeStructure(descriptor) {
//            encodeStringElement(descriptor, 0, value.message)
//            encodeStringElement(descriptor, 1, value.content.toString())
//        }
//    }
//
//    // 3. Define cómo convertir el JSON a un objeto
//    override fun deserialize(decoder: Decoder): Response.Success {
//        return decoder.decodeStructure(descriptor) {
//            var message: String? = null
//            var content: JsonElement? = null
//            // Lee los campos en cualquier orden
//            while (true) {
//                when (val index = decodeElementIndex(descriptor)) {
//                    0 -> message = decodeStringElement(descriptor, 0)
//                    1 -> content = dec(descriptor, 1)
//                    -1 -> break // Fin de la estructura
//                    else -> throw IllegalStateException("Unexpected index: $index")
//                }
//            }
//            // Construye el objeto final
//            Response.Success(
//                message = requireNotNull(message) { "Field 'message' is missing" },
//                content = requireNotNull(content) { "Field 'content' is missing" }
//            )
//        }
//    }
//}