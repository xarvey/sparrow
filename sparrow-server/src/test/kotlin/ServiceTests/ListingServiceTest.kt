import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.data.Datastore
import io.github.cmdq.sparrow.server.model.FilterParams
import io.github.cmdq.sparrow.server.model.Listing
import io.github.cmdq.sparrow.server.model.ListingType
import io.github.cmdq.sparrow.server.toJson
import io.github.cmdq.sparrow.server.toObject
import mocks.MockDatastore
import org.junit.Assert
import org.junit.Test
import java.util.*

public class ListingServiceTest {
    val mockListing = Listing(1, 1, ListingType.borrow, Date(), "Listing", "stuff", bounty = 0, closed = false)
    val mockFilter = FilterParams(ListingType.borrow)

    @Test fun testGetListing() {
        var called = false

        val service = Sparrow(object: MockDatastore() {
            override fun retrieveListing(id: Int): Listing? {
                assert(id == 1)
                called = true
                return mockListing
            }
        })

        val response = service.listings.getListing(1)
        assert(response.status == 200)
        assert(called == true)
        assert(response.body.toJson(service.gson) == mockListing.toJson(service.gson))
    }

    @Test fun testGetFilteredListings() {
        var called = false

        val service = Sparrow(object: MockDatastore() {
            override fun queryListings(filter: FilterParams): List<Listing> {
                assert(filter == mockFilter)
                called = true
                return listOf(mockListing)
            }
        })

        val response = service.listings.getFilteredListings(mockFilter)
        assert(response.status == 200)
        assert(called == true)
        assert(response.body.toJson(service.gson) == listOf(mockListing).toJson(service.gson))
    }

    @Test fun testCreateListing() {
        var called = false

        val service = Sparrow(object: MockDatastore() {
            override fun storeListing(newListing: Listing): Int {
                assert(newListing == mockListing)
                called = true
                return 1
            }
        })

        val response = service.listings.createListing(mockListing)
        assert(response.status == 200)
        assert(called == true)
        assert(response.body.toJson(service.gson) == 1.toJson(service.gson))
    }

    @Test fun testEditListing() {
        var called = false

        val service = Sparrow(object: MockDatastore() {
            override fun updateListing(listing: Listing) {
                assert(listing == mockListing.copy(id = 4))
                called = true
            }
        })

        val response = service.listings.editListing(mockListing.copy(id = 4))
        assert(response.status == 200)
        assert(called == true)
    }
}