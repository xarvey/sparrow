package io.github.cmdq.sparrow.server.endpoint

import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.model.Comment
import io.github.cmdq.sparrow.server.toJson
import io.github.cmdq.sparrow.server.toObject
import spark.Spark

fun setupComments(service: Sparrow) {
    val dir = "comments"

    Spark.get("/$dir/:id") { request, response ->
        val id = request.params("id").toInt()
        val result = service.comments.getComment(id)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.post("/$dir/listing/:id") { request, response ->
        val id = request.params("id").toInt()
        val comment: Comment = request.body().toObject(service.gson)
        val result = service.comments.createListingComment(id, comment)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.post("/$dir/user/:id") { request, response ->
        val id = request.params("id").toInt()
        val comment: Comment = request.body().toObject(service.gson)
        val result = service.comments.createUserComment(id, comment)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.delete("/$dir/:id") { request, response ->
        val id = request.params("id").toInt()
        val result = service.comments.deleteComment(id)
        response.status(result.status)
        result.body.toJson(service.gson)
    }
}