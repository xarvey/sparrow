package io.github.cmdq.sparrow.server.db

import io.github.cmdq.sparrow.server.data.Listing
import io.github.cmdq.sparrow.server.data.User
import io.github.cmdq.sparrow.server.data.UserCreation

public interface Datastore {
    fun retrieveUser(id: Int): User?
    fun updateUser(user: User)
    fun storeNewUser(newUser: UserCreation): Int

    fun retrieveListing(id: Int): Listing?
    fun updateListing(listing: Listing)
    fun storeListing(listing: Listing): Int
}