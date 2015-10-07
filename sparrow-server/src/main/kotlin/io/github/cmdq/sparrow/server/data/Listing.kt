package io.github.cmdq.sparrow.server.data

data class Listing(
        val id: Int,
        val owner: Int,
        val type: ListingType,
        val creationDate: Long,
        val title: String,
        val description: String,
        val tags: List<String> = emptyList(),
        val comments: List<String> = emptyList(),
        val bounty: Int
)