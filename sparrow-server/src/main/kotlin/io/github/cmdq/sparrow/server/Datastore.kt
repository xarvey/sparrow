package io.github.cmdq.sparrow.server

public interface Datastore {
    fun retrieveUser(id: Int): User?
    fun updateUser(user: User)
    fun storeNewUser(newUser: UserCreation): Int

    fun retrieveListing(id: Int): Listing?
    fun updateListing(listing: Listing)
    fun storeLendListing(listing: Listing): Int
}