package io.github.cmdq.sparrow.server.model

data class FilterParams(
        val type: ListingType? = null,
        val keywords: String? = null,
        val zipCode: List<String>? = null,
        val users: List<Int>? = null,
        val closed: Boolean? = null,
        val bountyMin: Int? = null,
        val bountyMax: Int? = null
)