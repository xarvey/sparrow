package mocks
import io.github.cmdq.sparrow.server.data.Datastore
import io.github.cmdq.sparrow.server.model.*

public open class MockDatastore: Datastore {
    override fun retrieveComment(id: Int): Comment? {
        throw UnsupportedOperationException()
    }

    override fun storeComment(comment: Comment): Int {
        throw UnsupportedOperationException()
    }

    override fun deleteComment(id: Int) {
        throw UnsupportedOperationException()
    }

    override fun retrieveUser(id: Int): User? {
        throw UnsupportedOperationException()
    }

    override fun retrieveUser(email: String): User? {
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

    override fun storeListing(newListing: Listing): Int {
        throw UnsupportedOperationException()
    }

    override fun deleteListing(id: Int) {
        throw UnsupportedOperationException()
    }

    override fun queryListings(filter: FilterParams): List<Listing> {
        throw UnsupportedOperationException()
    }

    override fun retrieveUserAuth(email: String): UserAuth? {
        throw UnsupportedOperationException()
    }
}
