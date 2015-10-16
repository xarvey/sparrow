package io.github.cmdq.sparrow.server.model

data class UserAuth(
        val passcode: String,
        val salt: String
)