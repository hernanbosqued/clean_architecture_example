import hernanbosqued.backend.controller.JsonController
import java.nio.file.Paths
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonControllerTest {

    @Test
    fun `primer test`() {
        val path = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/db.json"
        val controller = JsonController(path)
        val tasks = controller.allTasks()
        assertEquals(5, tasks.size)
    }
}