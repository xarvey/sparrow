package io.github.cmdq.sparrow.server.data

import io.github.cmdq.sparrow.server.model.*
import java.util.*

interface Datastore {

    fun retrieveUser(id: Int): User?
    fun updateUser(user: User)
    fun storeNewUser(newUser: UserCreation, userAuth: UserAuth): Int

    fun retrieveListing(id: Int): Listing?
    fun updateListing(listing: Listing)
    fun storeListing(newListing: Listing): Int
    fun deleteListing(id: Int)
    fun queryListings(filter: FilterParams): List<Listing>
}