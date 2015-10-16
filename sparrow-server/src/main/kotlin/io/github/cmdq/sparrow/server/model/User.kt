package io.github.cmdq.sparrow.server.model

data class User(
        val id: Int,
        val name: String,
        val email: String,
        val zipCode: String,
        val creationDate: Long,
        val friends: List<Int> = emptyList(),
        val borrowListings: List<Int> = emptyList(),
        val lendListings: List<Int> = emptyList(),
        val comments: List<Int> = emptyList(),
        val upVotes: List<Int> = emptyList(),
        val downVotes: List<Int> = emptyList()
)