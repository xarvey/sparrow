import com.google.gson.Gson
import io.github.cmdq.sparrow.server.Sparrow
import mocks.MockDatastore
import mocks.mockListing
import org.junit.Assert
import org.junit.Test

public class ListingEndpointTest {
    val endpoint = Sparrow(MockDatastore(), Gson()).listings

    @Test
    public fun testGetListingNegative() {
        val response = endpoint.getListing(-1)
        Assert.assertEquals(404, response.status)
    }

    @Test
    public fun testGetListingMax() {
        val response = endpoint.getListing(Int.MAX_VALUE)
        Assert.assertEquals(200, response.status)
    }

    @Test
    public fun testGetListingValid() {
        val response = endpoint.getListing(5)
        Assert.assertEquals(200, response.status)
    }

    @Test
    public fun testCreateListingValid() {
        try {
            endpoint.createListing(mockListing)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    public fun testEditListingValid() {
        try {
            endpoint.editListing(mockListing)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}