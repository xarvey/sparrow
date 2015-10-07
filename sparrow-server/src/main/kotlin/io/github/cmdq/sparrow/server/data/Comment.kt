package io.github.cmdq.sparrow.server.data

data class Comment(
        val id: Int,
        val owner: Int,
        val parent: Int,
        val creationDate: Long,
        val isPrivate: Boolean,
        val text: String
)