package io.github.cmdq.sparrow.server.model

data class ServiceResponse(
        val body: Any = "",
        val status: Int = 200
)