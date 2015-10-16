import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.model.FilterParams
import io.github.cmdq.sparrow.server.model.Listing
import io.github.cmdq.sparrow.server.model.ListingType
import io.github.cmdq.sparrow.server.model.User
import io.github.cmdq.sparrow.server.toJson
import mocks.MockDatastore
import org.junit.Test

public class ListingServiceTest {
    val mockListing = Listing(1, 1, ListingType.borrow, title="Listing", description="stuff")
    val mockFilter = FilterParams()

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

    fun testGetFilteredListings(testFilter: FilterParams, status: Int) {
        var called = false

        val service = Sparrow(object: MockDatastore() {
            override fun queryListings(filter: FilterParams): List<Listing> {
                assert(filter == testFilter)
                called = true
                return listOf(mockListing)
            }
        })

        val response = service.listings.getFilteredListings(testFilter)
        assert(response.status == status)
        if (status == 200) {
            assert(called == true)
            assert(response.body == listOf(mockListing))
        }
    }

    @Test fun testGetFilteredListingsValid() {
        testGetFilteredListings(mockFilter, 200)
    }

    @Test fun testGetFilteredListingsType() {
        testGetFilteredListings(mockFilter.copy(type = ListingType.borrow), 200)
    }

    @Test fun testGetFilteredListingsKeywords() {
        testGetFilteredListings(mockFilter.copy(keywords = "hello"), 200)
    }

    @Test fun testGetFilteredListingsZipCode() {
        testGetFilteredListings(mockFilter.copy(zipCode = listOf("47906")), 200)
    }

    @Test fun testGetFilteredListingsClosed() {
        testGetFilteredListings(mockFilter.copy(closed = true), 200)
    }

    @Test fun testGetFilteredListingsBounty() {
        testGetFilteredListings(mockFilter.copy(bountyMin = 4, bountyMax = 10), 200)
    }

    @Test fun testGetFilteredListingsAll() {
        testGetFilteredListings(mockFilter.copy(
                ListingType.borrow,
                "hello",listOf("47906"),
                closed = true,
                bountyMax = 14,
                bountyMin = 2
        ), 200)
    }

    @Test fun testGetFilteredKeywordsEmpty() {
        testGetFilteredListings(mockFilter.copy(keywords = ""), 400)
    }

    @Test fun testGetFilteredKeywordsLong() {
        testGetFilteredListings(mockFilter.copy(keywords = "a".repeat(141)), 400)
    }

    @Test fun testGetFilteredBountyHigh() {
        testGetFilteredListings(mockFilter.copy(bountyMax = 21), 400)
    }

    @Test fun testGetFilteredBountyLow() {
        testGetFilteredListings(mockFilter.copy(bountyMin = -1), 400)
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
        assert(response.body == 1)
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