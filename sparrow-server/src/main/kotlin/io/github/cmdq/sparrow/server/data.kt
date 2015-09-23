package io.github.cmdq.sparrow.server

data class ServiceResponse(
        val body: CharSequence = "",
        val status: Int = 200
)

abstract class SparrowData

data class UserCreation(
        val name: String,
        val email: String,
        val passcode: String,
        val zipCode: String
) : SparrowData()

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
) : SparrowData()

data class Listing(
        val id: Int,
        val owner: Int,
        val creationDate: Long,
        val title: String,
        val description: String,
        val tags: List<String>,
        val comments: List<String>,
        val bounty: Int
) : SparrowData()

data class Comment(
        val id: Int,
        val owner: Int,
        val parent: Int,
        val creationDate: Long,
        val isPrivate: Boolean,
        val text: String
) : SparrowData()

data class FilterParams(
        val keywords: String?,
        val zipCode: List<String>,
        val bountyMin: Int?,
        val bountyMax: Int?
) : SparrowData()