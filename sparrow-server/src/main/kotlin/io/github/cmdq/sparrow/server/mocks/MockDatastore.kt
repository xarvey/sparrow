package io.github.cmdq.sparrow.server.mocks

import io.github.cmdq.sparrow.server.Datastore
import io.github.cmdq.sparrow.server.Listing
import io.github.cmdq.sparrow.server.User
import io.github.cmdq.sparrow.server.UserCreation
import java.util.*

public class MockDatastore: Datastore {
    override fun retrieveUser(id: Int): User? {
        if (id < 0) return null
        else if (id >= Int.MAX_VALUE) return null
        else return User(0, "joe", "joe@email.com", "46526", Date().time)
    }

    override fun updateUser(user: User) {
        return
    }

    override fun storeNewUser(newUser: UserCreation): Int {
        return 0
    }

    override fun retrieveListing(id: Int): Listing? {
        throw UnsupportedOperationException()
    }

    override fun updateListing(listing: Listing) {
        throw UnsupportedOperationException()
    }

    override fun storeListing(listing: Listing): Int {
        throw UnsupportedOperationException()
    }
}