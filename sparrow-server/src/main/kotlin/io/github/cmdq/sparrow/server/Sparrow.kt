package io.github.cmdq.sparrow.server

import com.google.gson.Gson
import io.github.cmdq.sparrow.server.data.Listing
import io.github.cmdq.sparrow.server.data.ServiceResponse
import io.github.cmdq.sparrow.server.data.User
import io.github.cmdq.sparrow.server.data.UserCreation
import io.github.cmdq.sparrow.server.db.Datastore

class Sparrow(
        val datastore: Datastore,
        val gson: Gson = Gson()
) {
    public interface UserEndpoint {
        fun getUser(id: Int): ServiceResponse
        fun createUser(info: UserCreation): ServiceResponse
        fun editUser(user: User): ServiceResponse
    }

    public interface ListingEndpoint {
        fun getListing(id: Int): ServiceResponse
        fun createListing(listing: Listing): ServiceResponse
        fun editListing(listing: Listing): ServiceResponse
    }

    public val users = object : UserEndpoint {
        override fun getUser(id: Int): ServiceResponse {
            val user = datastore.retrieveUser(id)
            return when (user) {
                null -> ServiceResponse("User not found", 404)
                else -> ServiceResponse(user)
            }
        }

        override fun createUser(info: UserCreation): ServiceResponse {
            return ServiceResponse(datastore.storeNewUser(info))
        }

        override fun editUser(user: User): ServiceResponse {
            datastore.updateUser(user)
            return ServiceResponse()
        }
    }

    public val listings = object : ListingEndpoint {
        override fun getListing(id: Int): ServiceResponse {
            val listing = datastore.retrieveListing(id)
            return when (listing) {
                null -> ServiceResponse("Listing not found", 404)
                else -> ServiceResponse(listing)
            }
        }

        override fun createListing(listing: Listing): ServiceResponse {
            return ServiceResponse(datastore.storeListing(listing))
        }

        override fun editListing(listing: Listing): ServiceResponse {
            datastore.updateListing(listing)
            return ServiceResponse()
        }
    }
}