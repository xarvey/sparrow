package io.github.cmdq.sparrow.server

import com.google.gson.Gson
import com.google.gson.JsonParseException

public class Sparrow(
        val datastore: Datastore,
        val gson: Gson = Gson()
) {
    public val users = object: UserEndpoint {
        override fun getUser(id: Int): ServiceResponse {
            val user = datastore.retrieveUser(id)
            return when (user) {
                null -> ServiceResponse(
                        status = 404,
                        body = "User not found".toJson(gson)
                )
                else -> ServiceResponse(user.toJson(gson))
            }
        }

        override fun createUser(body: String): ServiceResponse {
            val newUser: UserCreation
            try {
                newUser = body.toObject(gson)
            } catch (e: JsonParseException) {
                return ServiceResponse(
                        status = 400,
                        body = "Bad request".toJson(gson)
                )
            }
            val id = datastore.storeNewUser(newUser)
            return ServiceResponse(id.toJson(gson))
        }

        override fun editUser(body: String): ServiceResponse {
            val user: User
            try {
                user = body.toObject(gson)
            } catch (e: JsonParseException) {
                return ServiceResponse(
                        status = 400,
                        body = "Bad request".toJson(gson)
                )
            }
            datastore.storeUser(user)
            return ServiceResponse()
        }
    }
}

public interface UserEndpoint {
    fun getUser(id: Int): ServiceResponse
    fun createUser(body: String): ServiceResponse
    fun editUser(body: String): ServiceResponse
}