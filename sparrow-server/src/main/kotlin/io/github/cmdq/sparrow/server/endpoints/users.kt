package io.github.cmdq.sparrow.server.endpoints

import com.google.gson.Gson
import com.google.gson.JsonParseException
import io.github.cmdq.sparrow.server.*
import spark.Spark

fun users(service: Sparrow) {
    val gson = Gson()
    val dir = "users"

    Spark.get("/$dir/:id") { request, response ->
        val id = request.params("id").toInt()
        val result = service.users.getUser(id)
        response.status(result.status)
        result.body.toJson(gson)
    }

    Spark.post("/$dir") { request, response ->
        try {
            val info: UserCreation = request.body().toObject(gson)
            val result = service.users.createUser(info)
            response.status(result.status)
            result.body.toJson(gson)
        } catch(e: JsonParseException) {
            response.status(400)
            "Bad request".toJson(gson)
        }
    }

    Spark.put("/$dir") { request, response ->
        try {
            val user: User = request.body().toObject(gson)
            val result = service.users.editUser(user)
            response.status(result.status)
            result.body.toJson(gson)
        } catch(e: JsonParseException) {
            response.status(400)
            "Bad request".toJson(gson)
        }
    }

    Spark.post("/$dir/friends/:id") { request, response ->

    }

    Spark.delete("/$dir/friends/:id") { request, response ->

    }

}