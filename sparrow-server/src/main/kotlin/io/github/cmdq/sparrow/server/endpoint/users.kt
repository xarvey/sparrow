package io.github.cmdq.sparrow.server.endpoint

import io.github.cmdq.sparrow.server.*
import io.github.cmdq.sparrow.server.data.User
import io.github.cmdq.sparrow.server.data.UserCreation
import spark.Spark

fun users(service: Sparrow) {
    val dir = "users"

    Spark.get("/$dir/:id") { request, response ->
        val id = request.params("id").toInt()
        val result = service.users.getUser(id)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.post("/$dir") { request, response ->
        val info: UserCreation = request.body().toObject(service.gson)
        val result = service.users.createUser(info)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.put("/$dir") { request, response ->
        val user: User = request.body().toObject(service.gson)
        val result = service.users.editUser(user)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.post("/$dir/friends/:id") { request, response ->

    }

    Spark.delete("/$dir/friends/:id") { request, response ->

    }

}