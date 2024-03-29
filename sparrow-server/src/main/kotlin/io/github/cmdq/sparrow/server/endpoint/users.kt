package io.github.cmdq.sparrow.server.endpoint

import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.model.ServiceResponse
import io.github.cmdq.sparrow.server.model.User
import io.github.cmdq.sparrow.server.model.UserCreation
import io.github.cmdq.sparrow.server.requireAuth
import io.github.cmdq.sparrow.server.toJson
import io.github.cmdq.sparrow.server.toObject
import spark.Spark

fun setupUsers(service: Sparrow) {
    val dir = "users"

    Spark.get("/login") { request, response ->
        service.requireAuth(request)
        val auth = request.headers("Authentication")
        val email = auth.substring(0, auth.indexOf(':'))
        val result = ServiceResponse(service.datastore.retrieveUser(email)!!)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

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
        service.requireAuth(request)
        val user: User = request.body().toObject(service.gson)
        val result = service.users.editUser(user)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.post("/$dir/friends/:id") { request, response ->
        service.requireAuth(request)
    }

    Spark.delete("/$dir/friends/:id") { request, response ->
        service.requireAuth(request)
    }

}
