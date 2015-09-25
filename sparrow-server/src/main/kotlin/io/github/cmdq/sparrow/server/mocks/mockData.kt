package io.github.cmdq.sparrow.server.mocks

import io.github.cmdq.sparrow.server.Listing
import io.github.cmdq.sparrow.server.ListingType
import io.github.cmdq.sparrow.server.User
import io.github.cmdq.sparrow.server.UserCreation
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
        Date().time,
        "stuff",
        "lots of stuff",
        bounty = 2
)
