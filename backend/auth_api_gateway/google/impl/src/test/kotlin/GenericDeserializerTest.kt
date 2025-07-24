package hernanbosqued.tests

import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.SerializationException
import org.junit.Test
import org.junit.jupiter.api.assertInstanceOf
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class GenericsDeserializationTest {
    @Test
    fun `Success with object Int from POST request`() = testApplicationWithConfiguredClient { client ->
        val expectedResponse = 1

        val response = client.post("/success/int") {
            contentType(ContentType.Application.Json)
        }.toDomainResponse<Int>()

        assertInstanceOf<DomainResponse.Success<Int>>(response)
        assertEquals(expectedResponse, response.content)
    }

    @Test
    fun `Success with object UserProfile from POST request`() = testApplicationWithConfiguredClient { client ->
        val expectedResponse = UserProfile("id", "name")

        val response = client.post("/success/user") {
            contentType(ContentType.Application.Json)
        }.toDomainResponse<UserProfile>()

        assertInstanceOf<DomainResponse.Success<UserProfile>>(response)
        assertEquals(expectedResponse, response.content)
    }


    @Test
    fun `Success with object String from POST request`() = testApplicationWithConfiguredClient { client ->
        val expectedResponse = "String Content"

        val response = client.post("/success/string") {
            contentType(ContentType.Application.Json)
        }.toDomainResponse<String>()

        assertInstanceOf<DomainResponse.Success<String>>(response)
        assertEquals(expectedResponse, response.content)
    }

    @Test
    fun `Success with object NULL from POST request`() = testApplicationWithConfiguredClient { client ->
        val expectedResponse = null

        val response = client.post("/success/null") {
            contentType(ContentType.Application.Json)
        }.toDomainResponse<String>()

        assertInstanceOf<DomainResponse.Success<Nothing>>(response)
        assertEquals(expectedResponse, response.content)
    }

    @Test
    fun `Throws SerializationException with NO SERIALIZABLE object from POST request`() = testApplicationWithConfiguredClient { client ->
        data class UserProfile2(val userId: String, val displayName: String)

        assertThrows<SerializationException> {
            client.post("/success/user") {
                contentType(ContentType.Application.Json)
            }.toDomainResponse<UserProfile2>()
        }
    }

    @Test
    fun `Error from POST request`() = testApplicationWithConfiguredClient { client ->
        val expectedResponse = DomainResponse.Error("Error", 1)

        val response = client.post("/error") {
            contentType(ContentType.Application.Json)
        }.toDomainResponse<Unit>()

        assertInstanceOf<DomainResponse.Error>(response)
        assertEquals(expectedResponse, response)
    }
}