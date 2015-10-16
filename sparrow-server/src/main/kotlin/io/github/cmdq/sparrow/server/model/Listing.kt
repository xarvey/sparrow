package io.github.cmdq.sparrow.server.model

import java.util.*

data class Listing(
        val id: Int,
        val owner: Int,
        val type: ListingType,
        val creationDate: Date,
        val title: String,
        val description: String,
        val tags: List<String> = emptyList(),
        val comments: List<Int> = emptyList(),
        val bounty: Int,
        val closed: Boolean
)