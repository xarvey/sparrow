import org.junit.Test
import io.github.cmdq.sparrow.server.getPort
import io.github.cmdq.sparrow.server.SparrowException
import kotlin.test.fail

public class MainKtTest {
    @Test
    public fun testGetPortLow() {
        val args = listOf("1024").toTypedArray()
        try {
            getPort(args)
            fail()
        } catch (e: SparrowException) {}
    }

    @Test
    public fun testGetPortHigh() {
        val args = listOf("65536").toTypedArray()
        try {
            getPort(args)
            fail()
        } catch (e: SparrowException) {}
    }

    @Test
    public fun testGetPortFew() {
        val args = listOf<String>().toTypedArray()
        try {
            getPort(args)
            fail()
        } catch (e: SparrowException) {}
    }

    @Test
    public fun testGetPortMany() {
        val args = listOf("5000", "6000").toTypedArray()
        try {
            getPort(args)
            fail()
        } catch (e: SparrowException) {}
    }

    @Test
    public fun testGetPortFormat() {
        val args = listOf("abc").toTypedArray()
        try {
            getPort(args)
            fail()
        } catch (e: SparrowException) {}
    }

    @Test
    public fun testGetPortCorrect1() {
        val args = listOf("1025").toTypedArray()
        try {
            getPort(args) == 1025
        } catch (e: SparrowException) {
            fail()
        }
    }

    @Test
    public fun testGetPortCorrect2() {
        val args = listOf("65535").toTypedArray()
        try {
            getPort(args) == 65535
        } catch (e: SparrowException) {
            fail()
        }
    }

    @Test
    public fun testGetPortCorrect3() {
        val args = listOf("9001").toTypedArray()
        try {
            getPort(args) == 9001
        } catch (e: SparrowException) {
            fail()
        }
    }
}
