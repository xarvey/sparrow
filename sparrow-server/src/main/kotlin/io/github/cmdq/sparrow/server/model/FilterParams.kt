package io.github.cmdq.sparrow.server.model

data class FilterParams(
        val type: ListingType?,
        val keywords: String?,
        val zipCode: List<String>?,
        val users: List<Int>?,
        val closed: Boolean?,
        val bountyMin: Int?,
        val bountyMax: Int?
)