import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.model.User
import io.github.cmdq.sparrow.server.model.UserCreation
import io.github.cmdq.sparrow.server.toJson
import mocks.MockDatastore
import org.junit.Test

public class UserServiceTest {

    @Test
    fun userCreationTest() {
        for (id in listOf(0, 500, Int.MAX_VALUE)) {

            val userCreation = UserCreation("name", "email@email", "pass", "12345")

            val sparrow = Sparrow(object : MockDatastore() {
                override fun storeNewUser(newUser: UserCreation): Int {
                    assert(newUser == userCreation)
                    return id
                }
            })

            with(sparrow.users.createUser(userCreation)) {
                assert(status == 200)
                assert(body == id.toJson(sparrow.gson))
            }
        }
    }

    @Test
    fun getUserTest() {
        val baseUser = User(0, "name", "email@email.com", "12345", 100000)

        for (testId in listOf(0, 500, Int.MAX_VALUE)) {
            for (testUser in listOf(null, baseUser.copy(id = testId))) {
                val sparrow = Sparrow(object : MockDatastore() {
                    override fun retrieveUser(id: Int): User? {
                        assert(testId == id)
                        return testUser
                    }
                })

                with(sparrow.users.getUser(testId)) {
                    assert(status == 200)
                    if (testUser == null)
                        assert(status == 404)
                    else {
                        assert(status == 200)
                        assert(body == testUser.toJson(sparrow.gson))
                    }
                }
            }
        }
    }
}