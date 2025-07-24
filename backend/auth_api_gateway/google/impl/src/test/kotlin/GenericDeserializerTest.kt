package hernanbosqued.tests

import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import org.junit.Test
import org.junit.jupiter.api.assertInstanceOf
import kotlin.test.assertEquals

class GenericsDeserializationTest {
    @Test
    fun `Success whit object Int from POST request`() = testApplicationWithConfiguredClient { client ->
        val expectedResponse = 1

        val response = client.post("/success/int") {
            contentType(ContentType.Application.Json)
        }.body<Response.Success>()

        val deserialized = response.decode<Int>()

        assertInstanceOf<Int>(deserialized)
        assertEquals(deserialized, 1)
    }

    @Test
    fun `Success whit object UserProfile from POST request`() = testApplicationWithConfiguredClient { client ->
        val userProfile = UserProfile("id", "name")

        val response = client.post("/success/user") {
            contentType(ContentType.Application.Json)
        }.body<Response.Success>()

        val deserialized = response.decode<UserProfile>()

        assertInstanceOf<UserProfile>(deserialized)
        assertEquals(deserialized, userProfile)
    }


    @Test
    fun `Success whit object String from POST request`() = testApplicationWithConfiguredClient { client ->
        val contentExpected = "String Content"

        val response = client.post("/success/string") {
            contentType(ContentType.Application.Json)
        }.body<Response.Success>()

        val deserialized = response.decode<String>()

        assertInstanceOf<String>(deserialized)
        assertEquals(contentExpected, deserialized)
    }

    @Test
    fun `Error from POST request`() = testApplicationWithConfiguredClient { client ->
        val messageExpected = Response.Error("Error", 1)
        val response = client.post("/error") {
            contentType(ContentType.Application.Json)
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }
}