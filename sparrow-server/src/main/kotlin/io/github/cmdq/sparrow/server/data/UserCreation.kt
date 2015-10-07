package io.github.cmdq.sparrow.server.data

data class UserCreation(
        val name: String,
        val email: String,
        val passcode: String,
        val zipCode: String
)
