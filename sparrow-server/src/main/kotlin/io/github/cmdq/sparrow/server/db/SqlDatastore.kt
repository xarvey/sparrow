package io.github.cmdq.sparrow.server.db

import io.github.cmdq.sparrow.server.data.Listing
import io.github.cmdq.sparrow.server.data.User
import io.github.cmdq.sparrow.server.data.UserCreation
import org.sql2o.Sql2o

class SqlDatastore(
        val dbUrl: String,
        val dbUser: String,
        val dbPass: String
) : Datastore {

    val db = Sql2o("jdbc:$dbUrl", dbUser, dbPass).open()

    override fun retrieveUser(id: Int): User? {
        throw UnsupportedOperationException()
    }

    override fun updateUser(user: User) {
        throw UnsupportedOperationException()
    }

    override fun storeNewUser(newUser: UserCreation): Int {
        throw UnsupportedOperationException()
    }

    override fun retrieveListing(id: Int): Listing? {
        throw UnsupportedOperationException()
    }

    override fun updateListing(listing: Listing) {
        throw UnsupportedOperationException()
    }

    override fun storeListing(listing: Listing): Int {
        throw UnsupportedOperationException()
    }

    override fun deleteListing(id: Int) {
        throw UnsupportedOperationException()
    }

}