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
    public fun testEmpty() {
        TestCase(emptyList()) { it == Args() }.test()
    }

    @Test
    public fun testShort() {
        TestCase(listOf("-p", "10000")) { it == Args(port = 10000) }.test()
        TestCase(listOf("-d", "mysql://example.com:6000/mydb")) { it == Args(dbUrl = "mysql://example.com:6000/mydb") }.test()
        TestCase(listOf("-u", "someUser")) { it == Args(dbUser = "someUser") }.test()
        TestCase(listOf("-a", "somePass")) { it == Args(dbPass = "somePass") }.test()
    }

    @Test
    public fun testLong() {
        TestCase(listOf("--port", "10000")) { it == Args(port = 10000) }.test()
        TestCase(listOf("--database", "mysql://example.com:6000/mydb")) { it == Args(dbUrl = "mysql://example.com:6000/mydb") }.test()
        TestCase(listOf("--username", "someUser")) { it == Args(dbUser = "someUser") }.test()
        TestCase(listOf("--auth", "somePass")) { it == Args(dbPass = "somePass") }.test()
    }

    @Test
    public fun testFull() {
        TestCase(listOf(
                "-p", "10000",
                "-d", "mysql://example.com:6000/mydb",
                "-u", "someUser",
                "-a", "somePass"
        )) { it == Args(
                port = 10000,
                dbUrl = "mysql://example.com:6000/mydb",
                dbUser = "someUser",
                dbPass = "somePass"
        ) }.test()

        TestCase(listOf(
                "-port", "10000",
                "-database", "mysql://example.com:6000/mydb",
                "-username", "someUser",
                "-auth", "somePass"
        )) { it == Args(
                port = 10000,
                dbUrl = "mysql://example.com:6000/mydb",
                dbUser = "someUser",
                dbPass = "somePass"
        ) }.test()
    }

    @Test
    public fun testUsage() {
        TestCase(listOf("-p", "9001", "fail"), fail = true).test()
        TestCase(listOf("-x", "abc"), fail = true).test()
        TestCase(listOf("-x"), fail = true).test()
        TestCase(listOf("x", "y", "z"), fail = true).test()
        TestCase(listOf("--something"), fail = true).test()
        TestCase(listOf("-p"), fail = true).test()
        TestCase(listOf("-d"), fail = true).test()
        TestCase(listOf("-u"), fail = true).test()
        TestCase(listOf("-a"), fail = true).test()
        TestCase(listOf("-p", "9001", "-a"), fail = true).test()
    }

    @Test
    public fun testChecks() {
        TestCase(listOf("-p", "1024"), fail = true).test()
        TestCase(listOf("-p", "65536"), fail = true).test()
        TestCase(listOf("-p", "abc"), fail = true).test()
        TestCase(listOf("-p", "1025")) { it == Args(port = 1025) }.test()
        TestCase(listOf("-p", "65535")) { it == Args(port = 65535) }.test()
        TestCase(listOf("-p", ""), fail = true).test()
        TestCase(listOf("-d", ""), fail = true).test()
    }
}