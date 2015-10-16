import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.model.UserCreation
import io.github.cmdq.sparrow.server.toJson
import mocks.MockDatastore
import org.junit.Test

public class UserServiceTest {

    @Test
    fun userCreationTest() {
        val userCreation = UserCreation("name", "email@email", "pass", "12345")

        var called = false
        val sparrow = Sparrow(object: MockDatastore() {
            override fun storeNewUser(newUser: UserCreation): Int {
                assert(newUser == userCreation)
                called = true
                return 10
            }
        })

        with(sparrow.users.createUser(userCreation)) {
            assert(called)
            assert(status == 200)
            assert(body == 10.toJson(sparrow.gson))
        }
    }
}