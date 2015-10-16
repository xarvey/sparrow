import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.model.User
import io.github.cmdq.sparrow.server.model.UserAuth
import io.github.cmdq.sparrow.server.model.UserCreation
import mocks.MockDatastore
import org.junit.Test

class AuthTest {
    val data = object: MockDatastore() {
        val user = User(10, "name", "a@b.c", "12345", 100000)

        override fun retrieveUser(email: String): User? =
                if (email == user.email)
                    user
                else
                    null

        override fun retrieveUserAuth(email: String): UserAuth? =
                if (email == user.email)
                    UserAuth(UserCreation(user.name, user.email, "pa:ss", user.zipCode))
                else
                    null
    }

    @Test
    fun AuthSuccess() {
        val sparrow = Sparrow(data)
        assert(sparrow.checkAuth("a@b.c:pa:ss"))
    }

    @Test
    fun AuthFailure1() {
        val sparrow = Sparrow(data)
        assert(!sparrow.checkAuth("a@b.c:wrong"))
    }
    @Test
    fun AuthFailure2() {
        val sparrow = Sparrow(data)
        assert(!sparrow.checkAuth("wrong@b.c:pass"))
    }

    @Test
    fun AuthFormat1() {
        val sparrow = Sparrow(data)
        assert(!sparrow.checkAuth(""))
    }

    @Test
    fun AuthFormat2() {
        val sparrow = Sparrow(data)
        assert(!sparrow.checkAuth("ad@sd:"))
    }

    @Test
    fun AuthFormat3() {
        val sparrow = Sparrow(data)
        assert(!sparrow.checkAuth(":something"))
    }

    @Test
    fun AuthFormat4() {
        val sparrow = Sparrow(data)
        assert(!sparrow.checkAuth("something"))
    }
}