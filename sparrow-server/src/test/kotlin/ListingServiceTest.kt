import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.data.Datastore
import io.github.cmdq.sparrow.server.model.FilterParams
import io.github.cmdq.sparrow.server.model.Listing
import io.github.cmdq.sparrow.server.model.ListingType
import io.github.cmdq.sparrow.server.model.User
import io.github.cmdq.sparrow.server.toJson
import io.github.cmdq.sparrow.server.toObject
import mocks.MockDatastore
import org.junit.Assert
import org.junit.Test
import java.util.*

public class ListingServiceTest {
    val mockListing = Listing(1, 1, ListingType.borrow, title="Listing", description="stuff")
    val mockFilter = FilterParams(ListingType.borrow)

    fun testGetListing(listing: Listing?, status: Int) {
        for (testId in listOf(0, 500, Int.MAX_VALUE)) {
            var called = false

            val service = Sparrow(object: MockDatastore() {
                override fun retrieveListing(id: Int): Listing? {
                    assert(id == testId)
                    called = true
                    return listing
                }
            })

            val response = service.listings.getListing(testId)
            assert(response.status == status)
            if (listing != null) {
                assert(called == true)
                assert(response.body == listing)
            }
        }
    }

    @Test fun testGetListingValid() {
        testGetListing(mockListing, 200)
    }

    @Test fun testGetListingDoesNotExist() {
        testGetListing(null, 404)
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
            override fun retrieveUser(id: Int): User? {
                return super.retrieveUser(id)
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