package io.github.cmdq.sparrow.server.model

import java.util.*

data class Comment(
        val id: Int,
        val owner: Int,
        val parent: Int,
        val creationDate: Date,
        val isPrivate: Boolean,
        val text: String
)