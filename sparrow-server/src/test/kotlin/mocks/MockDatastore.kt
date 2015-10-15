package mocks

import io.github.cmdq.sparrow.server.data.FilterParams
import io.github.cmdq.sparrow.server.db.Datastore
import io.github.cmdq.sparrow.server.data.Listing
import io.github.cmdq.sparrow.server.data.User
import io.github.cmdq.sparrow.server.data.UserCreation
import java.util.*

public class MockDatastore: Datastore {

    override fun retrieveUser(id: Int): User? {
        if (id < 0) return null
        else return mockUser
    }

    override fun updateUser(user: User) {
        return
    }

    override fun storeNewUser(newUser: UserCreation): Int {
        return 0
    }

    override fun retrieveListing(id: Int): Listing? {
        if (id < 0) return null
        else return mockListing
    }

    override fun updateListing(listing: Listing) {
        return
    }

    override fun storeListing(listing: Listing): Int {
        return 0
    }

    override fun deleteListing(id: Int) {
        return
    }

    override fun queryListings(filter: FilterParams): List<Listing> {
        return ArrayList()
    }
}