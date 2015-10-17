import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.model.User
import io.github.cmdq.sparrow.server.model.UserCreation
import mocks.MockDatastore
import org.junit.Test

public class UserServiceTest {

    //region Create user test cases

    class CreateUserTestCase(
            val userCreation: UserCreation,
            val status: Int = 200
    ) {
        fun test() {
            for (id in listOf(0, 500, Int.MAX_VALUE)) {

                val sparrow = Sparrow(object : MockDatastore() {
                    override fun storeNewUser(newUser: UserCreation): Int {
                        assert(status == 200)
                        assert(newUser == userCreation)
                        return id
                    }
                })

                val result = sparrow.users.createUser(userCreation)
                assert(status == result.status)
                if (status == 200) assert(result.body == id)
            }
        }
    }

    @Test
    fun createUserSuccess() {
        CreateUserTestCase(
                UserCreation("name", "email@email", "pass", "12345")
        ).test()
    }

    @Test
    fun createUserMissingName() {
        CreateUserTestCase(
                UserCreation("", "email@email", "pass", "12345"),
                status = 400
        ).test()
    }

    @Test
    fun createUserMissingEmail() {
        CreateUserTestCase(
                UserCreation("name", "", "pass", "12345"),
                status = 400
        ).test()
    }

    @Test
    fun createUserMissingPass() {
        CreateUserTestCase(
                UserCreation("name", "email@email", "", "12345"),
                status = 400
        ).test()
    }

    @Test
    fun createUserMissingZipCode() {
        CreateUserTestCase(
                UserCreation("name", "email@email", "pass", ""),
                status = 400
        ).test()
    }

    @Test
    fun createUserInvalidZipCode() {
        CreateUserTestCase(
                UserCreation("name", "email@email", "pass", "12E45"),
                status = 400
        ).test()
    }

    //endregion

    //region Get user test cases

    class GetUserTestCase(
            val resultUser: User?
    ) {
        fun test() {
            for (testId in listOf(0, 500, Int.MAX_VALUE)) {
                val testUser = resultUser?.copy(id = testId)
                val sparrow = Sparrow(object : MockDatastore() {
                    override fun retrieveUser(id: Int): User? {
                        assert(testId == id)
                        return testUser
                    }
                })

                with(sparrow.users.getUser(testId)) {
                    if (testUser == null)
                        assert(status == 404)
                    else {
                        assert(status == 200)
                        assert(body == testUser)
                    }
                }
            }
        }
    }

    @Test
    fun getUserSuccess() {
        GetUserTestCase(
                User(0, "name", "email@email.com", "12345", 100000)
        ).test()
    }

    @Test
    fun getUserNotFound() {
        GetUserTestCase(
                null
        ).test()
    }

    //endregion

    //region Edit user test cases

    class EditUserTestCase(
            val oldUser: User?,
            val newUser: User,
            val status: Int = 200
    ) {
        fun test() {
            for (testId in listOf(0, 500, Int.MAX_VALUE)) {
                val testOldUser = oldUser?.copy(id = testId)
                val testNewUser = newUser.copy(id = testId)
                val sparrow = Sparrow(object : MockDatastore() {
                    override fun updateUser(user: User) {
                        assert(status == 200)
                        assert(testOldUser != null)
                        assert(user == testNewUser)
                    }

                    override fun retrieveUser(id: Int): User? {
                        assert(id == testId)
                        return testOldUser
                    }
                })

                val result = sparrow.users.editUser(testNewUser)
                assert(status == result.status)
            }
        }
    }

    @Test
    fun editUserSuccess() {
        EditUserTestCase(
                User(0, "name", "email@email.com", "12345", 100000),
                User(0, "abc", "emaill@email.com", "12346", 100000)
        ).test()
    }

    @Test
    fun editUserNotFound() {
        EditUserTestCase(
                null,
                User(0, "name", "email@email.com", "12345", 100000),
                status = 404
        ).test()
    }

    @Test
    fun editUserEmptyName() {
        EditUserTestCase(
                User(0, "name", "email@email.com", "12345", 100000),
                User(0, "", "email@email.com", "12345", 100000),
                status = 400
        ).test()
    }

    @Test
    fun editUserEmptyEmail() {
        EditUserTestCase(
                User(0, "name", "email@email.com", "12345", 100000),
                User(0, "name", "", "12345", 100000),
                status = 400
        ).test()
    }

    @Test
    fun editUserEmptyZipCode() {
        EditUserTestCase(
                User(0, "name", "email@email.com", "12345", 100000),
                User(0, "name", "email@email.com", "", 100000),
                status = 400
        ).test()
    }

    @Test
    fun editUserInvalidZipCode() {
        EditUserTestCase(
                User(0, "name", "email@email.com", "12345", 100000),
                User(0, "name", "email@email.com", "12E45", 100000),
                status = 400
        ).test()
    }

    //endregion
}