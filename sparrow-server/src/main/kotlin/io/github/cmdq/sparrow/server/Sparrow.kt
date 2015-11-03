package io.github.cmdq.sparrow.server

import com.google.gson.Gson
import io.github.cmdq.sparrow.server.data.Datastore
import io.github.cmdq.sparrow.server.model.*
import java.util.*

class Sparrow(
        val datastore: Datastore,
        public val gson: Gson = Gson()
) {
    public interface CommentService {
        fun getComment(id: Int): ServiceResponse
        fun createListingComment(listingId: Int, comment: Comment): ServiceResponse
        fun createUserComment(userId: Int, comment: Comment): ServiceResponse
        fun deleteComment(id: Int): ServiceResponse
    }

    public interface FrontpageService {
        fun getPage(type: ListingType, page: Int, pageSize: Int): ServiceResponse
    }

    public interface UserService {
        fun getUser(id: Int): ServiceResponse
        fun createUser(info: UserCreation): ServiceResponse
        fun editUser(user: User): ServiceResponse
    }

    public interface ListingService {
        fun getListing(id: Int): ServiceResponse
        fun getFilteredListings(filter: FilterParams): ServiceResponse
        fun createListing(listing: Listing): ServiceResponse
        fun editListing(listing: Listing): ServiceResponse
        fun removeListing(id: Int): ServiceResponse
    }

    public val comments = object : CommentService {
        override fun getComment(id: Int): ServiceResponse {
            val comment = datastore.retrieveComment(id)
            return when (comment) {
                null -> ServiceResponse("Comment not found", 404)
                else -> ServiceResponse(comment)
            }
        }

        override fun createListingComment(listingId: Int, comment: Comment): ServiceResponse {
            if (comment.text.isEmpty())
                return ServiceResponse("Empty comment", 400)
            val id = datastore.storeComment(comment.copy(creationDate = Date()))
            val listing = datastore.retrieveListing(listingId) ?: return ServiceResponse("Listing not found", 404)
            val newListing = listing.copy(
                    comments = listing.comments + id
            )
            datastore.updateListing(newListing)
            return ServiceResponse(id)
        }

        override fun createUserComment(userId: Int, comment: Comment): ServiceResponse {
            if (comment.text.isEmpty())
                return ServiceResponse("Empty comment", 400)
            val id = datastore.storeComment(comment.copy(creationDate = Date()))
            val user = datastore.retrieveUser(userId) ?: return ServiceResponse("User not found", 404)
            val newUser = user.copy(
                    comments = user.comments + id
            )
            datastore.updateUser(newUser)
            return ServiceResponse(id)
        }

        override fun deleteComment(id: Int): ServiceResponse {
            val comment = datastore.retrieveComment(id)
                    ?: return ServiceResponse("Comment not found", 404)
            val listing = datastore.retrieveListing(comment.owner)
            if (listing != null) {
                val newListing = listing.copy(
                        comments = listing.comments - comment.id
                )
                datastore.updateListing(newListing)
            } else {
                val user = datastore.retrieveUser(comment.owner)
                        ?: return ServiceResponse("Owner not found", 404)
                val newUser = user.copy(
                        comments = user.comments - comment.id
                )
                datastore.updateUser(newUser)
            }
            datastore.deleteComment(id)
            return ServiceResponse()
        }

    }

    public val frontpage = object : FrontpageService {

        override fun getPage(type: ListingType, page: Int, pageSize: Int): ServiceResponse {
            if (page < 0 || pageSize <= 0)
                return ServiceResponse("Bad request", 400)

            val filter = FilterParams(type = type, closed = false)
            val listings = datastore.queryListings(filter).sortedByDescending { it.creationDate }

            val count = listings.count()
            val start = page * pageSize
            val end = Math.min(start + pageSize, count)

            if (start >= count) return ServiceResponse(emptyList<Listing>())

            return ServiceResponse(listings.subList(start, end))
        }

    }

    public val users = object : UserService {

        override fun getUser(id: Int): ServiceResponse {
            val user = datastore.retrieveUser(id)
            return when (user) {
                null -> ServiceResponse("User not found", 404)
                else -> ServiceResponse(user)
            }
        }

        override fun createUser(info: UserCreation): ServiceResponse {
            if (!info.email.isEmail())
                return ServiceResponse("Not a valid email", 400)
            if (!info.zipCode.isZipCode())
                return ServiceResponse("Not a valid zip code", 400)
            if (info.password.isEmpty())
                return ServiceResponse("Not a valid password", 400)
            if (info.name.isEmpty())
                return ServiceResponse("Not a valid name", 400)
            return ServiceResponse(datastore.storeNewUser(info))
        }

        override fun editUser(user: User): ServiceResponse {
            if (!user.email.isEmail())
                return ServiceResponse("Not a valid email", 400)
            if (!user.zipCode.isZipCode())
                return ServiceResponse("Not a valid zip code", 400)
            if (user.name.isEmpty())
                return ServiceResponse("Not a valid name", 400)
            if (datastore.retrieveUser(user.id) == null)
                return ServiceResponse("Not a valid user id", 404)
            datastore.updateUser(user)
            return ServiceResponse()
        }
    }

    public val listings = object : ListingService {
        override fun getListing(id: Int): ServiceResponse {
            val listing = datastore.retrieveListing(id)
            return when (listing) {
                null -> ServiceResponse("Listing not found", 404)
                else -> ServiceResponse(listing)
            }
        }

        override fun getFilteredListings(filter: FilterParams): ServiceResponse {
            if (filter.keywords?.length ?: 0 > 140)
                return ServiceResponse("Search is too long", 400)
            if (filter.keywords?.isEmpty() ?: false)
                return ServiceResponse("Search field is empty", 400)
            if (filter.bountyMin ?: 0 < 0)
                return ServiceResponse("Negative bounty min", 400)
            if (filter.bountyMax ?: 0 > 2000)
                return ServiceResponse("Bounty too high", 400)
            return ServiceResponse(datastore.queryListings(filter))
        }

        override fun createListing(listing: Listing): ServiceResponse {
            if (listing.bounty !in 0..2000)
                return ServiceResponse(status = 400)
            if (listing.title.count() !in 1..140)
                return ServiceResponse(status = 400)
            if (listing.description.isEmpty())
                return ServiceResponse(status = 400)
            val user = datastore.retrieveUser(listing.owner)
                    ?: return ServiceResponse("User not found")
            val id = datastore.storeListing(listing.copy(creationDate = Date()))
            if (listing.type == ListingType.borrow) {
                val user2 = user.copy(
                        borrowListings = user.borrowListings + listing.id
                )
                datastore.updateUser(user2)
            } else {
                val user2 = user.copy(
                        lendListings = user.lendListings + listing.id
                )
                datastore.updateUser(user2)
            }
            return ServiceResponse(id)
        }

        override fun editListing(listing: Listing): ServiceResponse {
            if (listing.bounty !in 0..2000)
                return ServiceResponse(status = 400)
            if (listing.title.count() !in 1..140)
                return ServiceResponse(status = 400)
            if (listing.description.isEmpty())
                return ServiceResponse(status = 400)
            datastore.updateListing(listing)
            return ServiceResponse()
        }

        override fun removeListing(id: Int): ServiceResponse {
            datastore.deleteListing(id)
            return ServiceResponse()
        }
    }

    fun checkAuth(auth: String?): Boolean {
        if (auth == null || !auth.contains(':'))
            return false

        val index = auth.indexOf(':')

        val email = auth.substring(0, index)
        val pass = auth.substring(index + 1)

        val user = datastore.retrieveUser(email)
        val userAuth = datastore.retrieveUserAuth(email)

        return user != null && userAuth != null && userAuth.passcode == (pass + userAuth.salt).hashString
    }
}