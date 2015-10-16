import io.github.cmdq.sparrow.server.Args
import io.github.cmdq.sparrow.server.parseArgs
import org.junit.Test

public class MainKtTest {
    class TestCase(
            val args: List<String>,
            val fail: Boolean = false,
            val check: (Args) -> Boolean = { true }
    ) {
        fun test() {
            try {
                val parsed = parseArgs(args.toTypedArray())
                assert(!fail)
                assert(check(parsed))
            } catch (e: Exception) {
                assert(fail)
            }
        }
    }

    @Test
    fun testEmpty() {
        TestCase(emptyList()) { it == Args() }.test()
    }

    @Test
    fun testShort() {
        TestCase(listOf("-p", "10000")) { it == Args(port = 10000) }.test()
        TestCase(listOf("-f", "test")) { it == Args(fileName = "test") }.test()
    }

    @Test
    fun testLong() {
        TestCase(listOf("--port", "10000")) { it == Args(port = 10000) }.test()
        TestCase(listOf("--file", "test")) { it == Args(fileName = "test") }.test()
    }

    @Test
    fun testFull() {
        TestCase(listOf(
                "-p", "10000",
                "-f", "test"
        )) { it == Args(
                port = 10000,
                fileName = "test"
        ) }.test()

        TestCase(listOf(
                "-port", "10000",
                "-file", "test"
        )) { it == Args(
                port = 10000,
                fileName = "test"
        ) }.test()
    }

    @Test
    fun testUsage() {
        TestCase(listOf("-p", "9001", "fail"), fail = true).test()
        TestCase(listOf("-x", "abc"), fail = true).test()
        TestCase(listOf("-x"), fail = true).test()
        TestCase(listOf("x", "y", "z"), fail = true).test()
        TestCase(listOf("--something"), fail = true).test()
        TestCase(listOf("-p"), fail = true).test()
        TestCase(listOf("-f"), fail = true).test()
        TestCase(listOf("-p", "9001", "-f"), fail = true).test()
    }

    @Test
    fun testChecks() {
        TestCase(listOf("-p", "1024"), fail = true).test()
        TestCase(listOf("-p", "65536"), fail = true).test()
        TestCase(listOf("-p", "abc"), fail = true).test()
        TestCase(listOf("-p", "1025")) { it == Args(port = 1025) }.test()
        TestCase(listOf("-p", "65535")) { it == Args(port = 65535) }.test()
        TestCase(listOf("-p", ""), fail = true).test()
        TestCase(listOf("-f", ""), fail = true).test()
    }
}