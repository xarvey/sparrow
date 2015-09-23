package io.github.cmdq.sparrow.server

data class UserCreation(
        val name: String,
        val email: String,
        val passcode: String,
        val zipCode: String
)

data class User(
        val id: Int,
        val name: String,
        val zipCode: String,
        val creationDate: Long,
        val friends: List<Int>,
        val borrowListings: List<Int>,
        val lendListings: List<Int>,
        val comments: List<Int>,
        val upVotes: List<Int>,
        val downVotes: List<Int>
)

data class Listing(
        val id: Int,
        val owner: Int,
        val creationDate: Long,
        val title: String,
        val description: String,
        val tags: List<String>,
        val comments: List<String>,
        val bounty: Int
)

data class Comment(
        val id: Int,
        val owner: Int,
        val parent: Int,
        val creationDate: Long,
        val isPrivate: Boolean,
        val text: String
)

data class FilterParams(
        val keywords: String?,
        val zipCode: List<String>,
        val bountyMin: Int?,
        val bountyMax: Int?
)