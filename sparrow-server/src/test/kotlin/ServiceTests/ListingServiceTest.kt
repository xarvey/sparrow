import com.google.gson.Gson
import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.toJson
import mocks.MockDatastore
import mocks.mockListing
import org.junit.Assert
import org.junit.Test

public class ListingServiceTest {
    val service = Sparrow(MockDatastore(), Gson())
    val listings = service.listings

    @Test
    public fun testGetListingNegative() {
        val response = listings.getListing(-1)
        Assert.assertEquals(404, response.status)
    }

    @Test
    public fun testGetListingMax() {
        val response = listings.getListing(Int.MAX_VALUE)
        Assert.assertEquals(200, response.status)
    }

    @Test
    public fun testGetListingValid() {
        val response = listings.getListing(5)
        Assert.assertEquals(200, response.status)
    }

    @Test
    public fun testCreateListingValid() {
        try {
            listings.createListing(mockListing)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    public fun testEditListingValid() {
        try {
            listings.editListing(mockListing)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    public fun testDeleteListingValid() {
        val response = listings.createListing(mockListing)
        Assert.assertEquals(200, response.status)
        val id = response.body as Int
        val delete = listings.removeListing(id)
        Assert.assertEquals(200, delete.status)
    }
}