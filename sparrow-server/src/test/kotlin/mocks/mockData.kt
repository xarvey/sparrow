package mocks

import io.github.cmdq.sparrow.server.model.*
import java.util.*

val mockUserCreation = UserCreation(
        "joe",
        "joe@mail.com",
        "12345",
        "46526"
)

val mockUser = User(
        0,
        "joe",
        "joe@mail.com",
        "46526",
        Date().time
)

val mockListing = Listing(
        0,
        0,
        ListingType.lend,
        Date(),
        "stuff",
        "lots of stuff",
        bounty = 2,
        closed = false
)

val mockComment = Comment(
        0,
        0,
        0,
        Date().time,
        false,
        "comment"
)