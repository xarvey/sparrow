import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.model.FilterParams
import io.github.cmdq.sparrow.server.model.Listing
import io.github.cmdq.sparrow.server.model.ListingType
import mocks.MockDatastore
import org.junit.Test
import java.util.*
import kotlin.test.fail

class FrontpageServiceTest {

    companion object {
        fun generateListings(type: ListingType, n: Int) = (1..n) map { i ->
            Listing(n + i, i, type, Date(i.toLong()), "Listing $i", "A $type listing")
        }
    }

    val types = ListingType.values().toList()

    class MockData(val type: ListingType, val n: Int): MockDatastore() {
        override fun queryListings(filter: FilterParams): List<Listing> {
            assert(filter.closed == false)
            assert(filter.type == type)
            var result = generateListings(type, n)
            Collections.shuffle(result.toArrayList())
            return result
        }
    }

    @Test
    fun invalid() {
        types forEach { type ->
            val sparrow = Sparrow(MockDatastore())
            assert(sparrow.frontpage.getPage(type, -1, 10).status == 400)
            assert(sparrow.frontpage.getPage(type, 0, -1).status == 400)
            assert(sparrow.frontpage.getPage(type, 0, 0).status == 400)
        }
    }

    @Test
    fun empty() {
        types forEach { type ->
            val sparrow = Sparrow(MockData(type, 0))
            val result = sparrow.frontpage.getPage(type, page = 0, pageSize = 10)
            assert(result.body == emptyList<Listing>())
        }
    }

    @Test
    fun firstPagePartial() {
        val count = 3
        val pageSize = 5
        types forEach { type ->
            val sparrow = Sparrow(MockData(type, count))
            val result = sparrow.frontpage.getPage(type, page = 0, pageSize = pageSize)
            val it = result.body
            when (it) {
                is List<*> -> {
                    assert (it.count() == count)
                }
                else -> fail()
            }
        }
    }

    @Test
    fun firstPageFull() {
        val count = 5
        val pageSize = 5
        types forEach { type ->
            val sparrow = Sparrow(MockData(type, count))
            val result = sparrow.frontpage.getPage(type, page = 0, pageSize = pageSize)
            val it = result.body
            when (it) {
                is List<*> -> {
                    assert (it.count() == count)
                }
                else -> fail()
            }
        }
    }

    @Test
    fun innerPage() {
        val total = 20
        val pageSize = 5
        types forEach { type ->
            val sparrow = Sparrow(MockData(type, total))
            val result = sparrow.frontpage.getPage(type, page = 1, pageSize = pageSize)
            val it = result.body
            when (it) {
                is List<*> -> {
                    assert (it.count() == pageSize)
                }
                else -> fail()
            }
        }
    }

    @Test
    fun lastPagePartial() {
        val total = 18
        val count = 3
        val pageSize = 5
        types forEach { type ->
            val sparrow = Sparrow(MockData(type, total))
            val result = sparrow.frontpage.getPage(type, page = 3, pageSize = pageSize)
            val it = result.body
            when (it) {
                is List<*> -> {
                    assert (it.count() == count)
                }
                else -> fail()
            }
        }
    }

    @Test
    fun lastPageFull() {
        val total = 20
        val pageSize = 5
        types forEach { type ->
            val sparrow = Sparrow(MockData(type, total))
            val result = sparrow.frontpage.getPage(type, page = 3, pageSize = pageSize)
            val it = result.body
            when (it) {
                is List<*> -> {
                    assert (it.count() == pageSize)
                }
                else -> fail()
            }
        }
    }

    @Test
    fun beyond() {
        val total = 5
        val pageSize = 2
        types forEach { type ->
            val sparrow = Sparrow(MockData(type, total))
            val result = sparrow.frontpage.getPage(type, page = 10, pageSize = pageSize)
            val it = result.body
            when (it) {
                is List<*> -> {
                    assert (it == emptyList<Listing>())
                }
                else -> fail()
            }
        }
    }
}