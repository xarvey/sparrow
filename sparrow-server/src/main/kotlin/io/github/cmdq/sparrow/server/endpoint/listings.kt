package io.github.cmdq.sparrow.server.endpoint

import io.github.cmdq.sparrow.server.data.Listing
import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.toJson
import io.github.cmdq.sparrow.server.toObject
import spark.Spark

fun listings(service: Sparrow) {
    val dir = "listings"

    Spark.get("/$dir/:id") { request, response ->
        val id = request.params("id").toInt()
        val result = service.listings.getListing(id)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.put("/$dir") { request, response ->
        val listing: Listing = request.body().toObject(service.gson)
        val result = service.listings.editListing(listing)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.put("/$dir/filter") { request, response ->

    }

    Spark.post("/$dir") { request, response ->
        val listing: Listing = request.body().toObject(service.gson)
        val result = service.listings.createListing(listing)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.delete("/$dir/id") { request, response ->

    }
}