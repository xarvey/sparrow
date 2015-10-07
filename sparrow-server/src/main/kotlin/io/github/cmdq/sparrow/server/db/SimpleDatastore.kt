package io.github.cmdq.sparrow.server.db

import io.github.cmdq.sparrow.server.data.Listing
import io.github.cmdq.sparrow.server.data.User
import io.github.cmdq.sparrow.server.data.UserCreation
import java.util.*

public class SimpleDatastore : Datastore {
    private var nextUserId = 0
    private var nextListingId = 0
    private val userTable = HashMap<Int, User>()
    private val listingTable = HashMap<Int, Listing>()

    override public fun retrieveUser(id: Int): User? {
        return userTable[id]
    }

    override public fun updateUser(user: User) {
        userTable[user.id] = user
    }

    override public fun storeNewUser(newUser: UserCreation): Int {
        val user = User(
                id = nextUserId++,
                name = newUser.name,
                email = newUser.email,
                zipCode = newUser.zipCode,
                creationDate = Date().time
        )
        userTable[user.id] = user
        return user.id
    }

    override fun retrieveListing(id: Int): Listing? = listingTable[id]

    override fun updateListing(listing: Listing) {
        listingTable[listing.id] = listing
    }

    override fun storeListing(listing: Listing): Int {
        val newListing = listing.copy(id = nextListingId++)
        listingTable[newListing.id] = newListing
        return newListing.id
    }

}