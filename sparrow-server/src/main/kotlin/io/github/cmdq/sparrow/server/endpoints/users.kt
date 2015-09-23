package io.github.cmdq.sparrow.server.endpoints

import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.UserCreation
import io.github.cmdq.sparrow.server.toObject
import spark.Spark

fun users(service: Sparrow) {
    val dir = "users"

    Spark.get("/$dir/:id") { request, response ->
        val id = request.params("id").toInt()
        val result = service.users.getUser(id)
        response.status(result.status)
        result.body
    }

    Spark.post("/$dir") { request, response ->
        val result = service.users.createUser(request.body())
        response.status(result.status)
        result.body
    }

    Spark.put("/$dir") { request, response ->
        val result = service.users.createUser(request.body())
        response.status(result.status)
        result.body
    }

    Spark.post("/$dir/friends/add/:id") { request, response ->

    }

    Spark.delete("/$dir/friends/remove/:id") { request, response ->

    }
}