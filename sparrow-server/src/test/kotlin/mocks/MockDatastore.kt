package mocks
import io.github.cmdq.sparrow.server.data.Datastore
import io.github.cmdq.sparrow.server.model.*

public class MockDatastore: Datastore {
    override fun storeComment(comment: Comment): Int = 0

    override fun retrieveComment(id: Int): Comment? = mockComment

    override fun queryListings(filter: FilterParams): List<Listing> = emptyList()

    override fun retrieveUser(id: Int): User? = if (id < 0) null else mockUser

    override fun updateUser(user: User) {}

    override fun storeNewUser(newUser: UserCreation, userAuth: UserAuth): Int = 0

    override fun retrieveListing(id: Int): Listing? = if (id < 0) null else mockListing

    override fun updateListing(listing: Listing) { }

    override fun storeListing(newListing: Listing): Int = 0

    override fun deleteListing(id: Int) { }

    override fun deleteComment(id: Int) {
        throw UnsupportedOperationException()
    }

}
