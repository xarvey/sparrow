package mocks
import io.github.cmdq.sparrow.server.data.Datastore
import io.github.cmdq.sparrow.server.model.*

public class MockDatastore: Datastore {
    override fun queryListings(filter: FilterParams): List<Listing> {
        return emptyList()
    }

    override fun retrieveUser(id: Int): User? {
        if (id < 0) return null
        else return mockUser
    }

    override fun updateUser(user: User) {
        return
    }

    override fun storeNewUser(newUser: UserCreation, userAuth: UserAuth): Int {
        return 0
    }

    override fun retrieveListing(id: Int): Listing? {
        if (id < 0) return null
        else return mockListing
    }

    override fun updateListing(listing: Listing) {
        return
    }

    override fun storeListing(newListing: Listing): Int {
        return 0
    }

    override fun deleteListing(id: Int) {
        return
    }


}