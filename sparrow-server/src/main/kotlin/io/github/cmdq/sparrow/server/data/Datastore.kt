package io.github.cmdq.sparrow.server.data

import io.github.cmdq.sparrow.server.model.*

interface Datastore {

    fun retrieveComment(id: Int): Comment?
    fun storeComment(comment: Comment): Int
    fun deleteComment(id: Int)

    fun retrieveUser(id: Int): User?
    fun retrieveUser(email: String): User?
    fun updateUser(user: User)

    fun storeNewUser(newUser: UserCreation, userAuth: UserAuth): Int
    fun retrieveListing(id: Int): Listing?
    fun updateListing(listing: Listing)
    fun storeListing(newListing: Listing): Int
    fun deleteListing(id: Int)
    fun queryListings(filter: FilterParams): List<Listing>
    fun retrieveUserAuth(email: String): UserAuth?
}