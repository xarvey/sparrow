import com.google.gson.Gson
import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.User
import io.github.cmdq.sparrow.server.UserCreation
import io.github.cmdq.sparrow.server.mocks.MockDatastore
import org.junit.Assert
import org.junit.Test
import java.util.*

public class UserEndpointTest {
    val endpoint = Sparrow(MockDatastore(), Gson()).users

    @Test
    public fun testGetUserNegative() {
        val response = endpoint.getUser(-1)
        Assert.assertEquals(404, response.status)
    }

    @Test
    public fun testGetUserMax() {
        val response = endpoint.getUser(Int.MAX_VALUE)
        Assert.assertEquals(404, response.status)
    }

    @Test
    public fun testGetUserValid() {
        val response = endpoint.getUser(5)
        Assert.assertEquals(200, response.status)
    }

    @Test
    public fun testCreateUserValid() {
        try {
            val info = UserCreation("joe", "joe@mail.com", "12345", "46526")
            endpoint.createUser(info)
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    public fun testEditUserValid() {
        try {
            val user = User(0, "joe", "joe@mail.com", "46526", Date().time)
            endpoint.editUser(user)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}