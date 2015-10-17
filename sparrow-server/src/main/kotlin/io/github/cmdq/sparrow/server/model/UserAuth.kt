package io.github.cmdq.sparrow.server.model

import io.github.cmdq.sparrow.server.hashString

data class UserAuth(
        val salt: String,
        val passcode: String
) {
    constructor(newUser: UserCreation) : this(
            salt = newUser.name.hashString,
            passcode = (newUser.password + newUser.name.hashString).hashString
    )
}