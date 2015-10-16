package io.github.cmdq.sparrow.server.model

data class UserCreation(
        val name: String,
        val email: String,
        val password: String,
        val zipCode: String
)
