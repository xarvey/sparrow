package io.github.cmdq.sparrow.server

import com.google.gson.Gson
import io.github.cmdq.sparrow.server.data.Datastore
import io.github.cmdq.sparrow.server.model.*

class Sparrow(
        val datastore: Datastore,
        public val gson: Gson = Gson()
) {
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

    public val users = object : UserService {
        override fun getUser(id: Int): ServiceResponse {
            val user = datastore.retrieveUser(id)
            return when (user) {
                null -> ServiceResponse("User not found", 404)
                else -> ServiceResponse(user)
            }
        }

        override fun createUser(info: UserCreation): ServiceResponse {
            val salt = info.name.hashCode().toString()
            return ServiceResponse(datastore.storeNewUser(info,
                    UserAuth(
                            passcode = (info.password + salt).hashCode().toString(),
                            salt = salt
                    )
            ))
        }

        override fun editUser(user: User): ServiceResponse {
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
            if (filter.keywords?.length() ?: 0 > 140)
                return ServiceResponse("Search is too long", 404)
            if (filter.keywords.isNullOrEmpty())
                return ServiceResponse("Search is empty", 404)
            if (filter.bountyMin ?: 0 < 0)
                return ServiceResponse("Negative bounty min", 404)
            if (filter.bountyMax ?: 0 > 20)
                return ServiceResponse("Bounty too high", 404)
            return ServiceResponse(datastore.queryListings(filter))
        }

        override fun createListing(listing: Listing): ServiceResponse {
            return ServiceResponse(datastore.storeListing(listing))
        }

        override fun editListing(listing: Listing): ServiceResponse {
            datastore.updateListing(listing)
            return ServiceResponse()
        }

        override fun removeListing(id: Int): ServiceResponse {
            datastore.deleteListing(id)
            return ServiceResponse()
        }
    }
}