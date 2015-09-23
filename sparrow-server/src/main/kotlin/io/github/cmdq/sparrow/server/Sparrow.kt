package io.github.cmdq.sparrow.server

import com.google.gson.Gson
import com.google.gson.JsonParseException

public class Sparrow(
        val datastore: MockDatastore,
        val gson: Gson = Gson()
) {
    public val users = object: UserEndpoint {
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

    public val listings = object: ListingEndpoint {
        override fun getListing(id: Int): ServiceResponse {
            val listing = datastore.retrieveListing(id)
            return when (listing) {
                null -> ServiceResponse("Listing not found", 404)
                else -> ServiceResponse(listing)
            }
        }

        override fun createLendListing(listing: Listing): ServiceResponse {
            return ServiceResponse(datastore.storeLendListing(listing))
        }

        override fun editListing(listing: Listing): ServiceResponse {
            datastore.updateListing(listing)
            return ServiceResponse()
        }
    }
}

public interface UserEndpoint {
    fun getUser(id: Int): ServiceResponse
    fun createUser(info: UserCreation): ServiceResponse
    fun editUser(user: User): ServiceResponse
}

public interface ListingEndpoint {
    fun getListing(id: Int): ServiceResponse
    fun createLendListing(listing: Listing): ServiceResponse
    fun editListing(listing: Listing): ServiceResponse
}