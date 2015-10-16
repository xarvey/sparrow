package io.github.cmdq.sparrow.server.data

import io.github.cmdq.sparrow.server.model.*
import java.util.*

class FlatDatastore(
        val start: FlatDatastore.Data,
        val save: (FlatDatastore.Data) -> Unit
) : Datastore {
    open class Data(
            open val nextId: Int = 0,
            open val users: Map<Int, User> = emptyMap(),
            open val auth: Map<Int, UserAuth> = emptyMap(),
            open val listings: Map<Int, Listing> = emptyMap(),
            open val comments: Map<Int, Comment> = emptyMap()
    )

    object persistence : Data() {
        override var nextId = 0
        override val users = HashMap<Int, User>()
        override val auth = HashMap<Int, UserAuth>()
        override val listings = HashMap<Int, Listing>()
        override val comments = HashMap<Int, Comment>()
        fun genNextId(): Int = nextId++
    }

    init {
        persistence.apply {
            nextId = start.nextId
            users.putAll(start.users)
            auth.putAll(start.auth)
            listings.putAll(start.listings)
            comments.putAll(start.comments)
            save(this)
        }
    }

    override fun retrieveUser(id: Int): User? = persistence.users[id]

    override fun updateUser(user: User) {
        persistence.users[user.id] = user
        save(persistence)
    }

    override fun storeNewUser(newUser: UserCreation, userAuth: UserAuth): Int {
        val newId = persistence.genNextId()
        persistence.users[newId] = User(
                id = newId,
                name = newUser.name,
                email = newUser.email,
                zipCode = newUser.zipCode,
                creationDate = Date().date.toLong()
        )
        persistence.auth[newId] = userAuth

        save(persistence)
        return newId
    }

    override fun retrieveListing(id: Int): Listing? = persistence.listings[id]

    override fun updateListing(listing: Listing) {
        persistence.listings[listing.id] = listing
        save(persistence)
    }

    override fun storeListing(newListing: Listing): Int {
        val newId = persistence.genNextId()
        persistence.listings[newId] = newListing.copy(
                id = newId
        )
        save(persistence)
        return newId
    }

    override fun deleteListing(id: Int) {
        persistence.listings.remove(id)
        save(persistence)
    }

    override fun queryListings(filter: FilterParams): List<Listing> {
        return persistence.listings.values().filter {
            filter.type == null || filter.type == it.type
        }.filter {
            filter.keywords == null || it.tags.fold(false) { r, tag -> r || filter.keywords.contains(tag) }
        }.filter {
            filter.zipCode == null || filter.zipCode.contains(persistence.users[it.owner]?.zipCode)
        }.filter {
            filter.users == null || filter.users.contains(it.owner)
        }.filter {
            filter.closed == null || filter.closed == it.closed
        }.filter {
            filter.bountyMax == null || it.bounty <= filter.bountyMax
        }.filter {
            filter.bountyMin == null || it.bounty >= filter.bountyMin
        }
    }

    override fun retrieveComment(id: Int): Comment? {
        return persistence.comments[id]
    }

    override fun storeComment(comment: Comment): Int {
        val newId = persistence.genNextId()
        persistence.comments[newId] = comment.copy(
                id = newId
        )
        save(persistence)
        return newId
    }

    override fun deleteComment(id: Int) {
        persistence.comments.remove(id)
        save(persistence)
    }
}
