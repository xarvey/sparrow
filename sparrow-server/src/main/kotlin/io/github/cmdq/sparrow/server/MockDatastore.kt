package io.github.cmdq.sparrow.server

import java.util.*

public class MockDatastore : Datastore {
    private var nextUserId = 0
    private var nextListingId = 0
    private val userTable = HashMap<Int, User>()
    private val lendTable = HashMap<Int, Listing>()
    private val borrowTable = HashMap<Int, Listing>()

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

    override fun retrieveListing(id: Int): Listing? {
        return if (lendTable[id] != null) lendTable[id] else borrowTable[id]
    }

    override fun updateListing(listing: Listing) {
        if (lendTable[listing.id] != null)
            lendTable[listing.id] = listing
        else
            borrowTable[listing.id] = listing
    }

    override fun storeLendListing(listing: Listing): Int {
        val newListing = listing.copy(id = nextListingId++)
        lendTable[newListing.id] = newListing
        return newListing.id
    }

}