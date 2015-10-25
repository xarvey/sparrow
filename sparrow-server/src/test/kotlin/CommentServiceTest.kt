import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.model.Comment
import io.github.cmdq.sparrow.server.model.Listing
import io.github.cmdq.sparrow.server.model.ListingType
import io.github.cmdq.sparrow.server.model.User
import mocks.MockDatastore
import org.junit.Test
import java.util.*

class CommentServiceTest {
    val mockListing = Listing(1, 1, ListingType.borrow, title="Listing", description="stuff")
    val mockComment = Comment(1, 1, 1, Date(), false, "Stuff")
    val mockUser = User(1, "", "", "", Date().time)

    fun testGetComment(comment: Comment?, status: Int) {
        for (testId in listOf(0, 500, Int.MAX_VALUE)) {
            var called = false

            val service = Sparrow(object : MockDatastore() {
                override fun retrieveComment(id: Int): Comment? {
                    assert(id == testId)
                    called = true
                    return comment
                }
            })

            val response = service.comments.getComment(testId)
            assert(response.status == status)
            if (comment != null) {
                assert(called == true)
                assert(response.body == comment)
            }
        }
    }

    @Test fun testGetCommentValid() {
        testGetComment(Comment(1, 1, 1, Date(), false, "Stuff"), 200)
    }

    @Test fun testGetCommentNotFound() {
        testGetComment(null, 404)
    }

    fun testCreateListingComment(testListing: Listing?, testComment: Comment, status: Int) {
        var called = false

        val service = Sparrow(object: MockDatastore() {
            override fun storeComment(comment: Comment): Int {
                assert(comment == testComment)
                called = true
                return comment.id
            }
            override fun retrieveListing(id: Int): Listing? {
                return testListing
            }
            override fun updateListing(listing: Listing) {

            }
        })

        val result = service.comments.createListingComment(testComment.owner, testComment)
        assert(result.status == status)
        if (status == 200) {
            assert(result.body == testComment.id)
            assert(called == true)
        }
    }

    @Test fun testCreateListingCommentValid() {
        testCreateListingComment(mockListing, Comment(1, 1, 1, Date(), false, "Stuff"), 200)
    }

    @Test fun testCreateListingCommentNotFound() {
        testCreateListingComment(null, Comment(1, 1, 1, Date(), false, "Stuff"), 404)
    }

    @Test fun testCreateListingCommentEmpty() {
        testCreateListingComment(mockListing, Comment(1,1,1,Date(), false, ""), 400)
    }

    fun testCreateUserComment(testUser: User?, testComment: Comment, status: Int) {
        var called = false

        val service = Sparrow(object: MockDatastore() {
            override fun storeComment(comment: Comment): Int {
                assert(comment == testComment)
                called = true
                return comment.id
            }
            override fun retrieveUser(id: Int): User? {
                return testUser
            }
            override fun updateUser(user: User) {

            }
        })

        val result = service.comments.createUserComment(testComment.owner, testComment)
        assert(result.status == status)
        if (status == 200) {
            assert(result.body == testComment.id)
            assert(called == true)
        }
    }

    @Test fun testCreateUserCommentValid() {
        testCreateUserComment(mockUser, Comment(1, 1, 1, Date(), false, "Stuff"), 200)
    }

    @Test fun testCreateUserCommentNotFound() {
        testCreateUserComment(null, Comment(1, 1, 1, Date(), false, "Stuff"), 404)
    }

    @Test fun testCreateUserCommentEmpty() {
        testCreateUserComment(mockUser, Comment(1,1,1,Date(), false, ""), 400)
    }

    fun testRemoveComment(testListing: Listing?, testUser: User?, testComment: Comment?, testId: Int, status: Int) {
        var called = false

        val service = Sparrow(object: MockDatastore() {
            override fun deleteComment(id: Int) {
                assert(id == testId)
                called = true
            }
            override fun retrieveComment(id: Int): Comment? {
                return testComment
            }
            override fun retrieveListing(id: Int): Listing? {
                return testListing
            }
            override fun retrieveUser(id: Int): User? {
                return testUser
            }
            override fun updateListing(listing: Listing) {
            }
            override fun updateUser(user: User) {
            }
        })

        val result = service.comments.deleteComment(testId)
        assert(result.status == status)
        if (status == 200) assert(called == true)
    }

    @Test fun testRemoveCommentValid() {
        testRemoveComment(mockListing, null, mockComment, 2, 200)
    }

    @Test fun testRemoveCommentNoListing() {
        testRemoveComment(null, mockUser, mockComment, 2, 200)
    }

    @Test fun testRemoveCommentNeither() {
        testRemoveComment(null, null, mockComment, 2, 404)
    }

    @Test fun testRemoveCommentNotFount() {
        testRemoveComment(null, null, null, 2, 404)
    }
}