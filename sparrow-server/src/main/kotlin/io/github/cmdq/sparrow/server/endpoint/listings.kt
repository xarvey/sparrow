package io.github.cmdq.sparrow.server.endpoint

import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.model.FilterParams
import io.github.cmdq.sparrow.server.model.Listing
import io.github.cmdq.sparrow.server.requireAuth
import io.github.cmdq.sparrow.server.toJson
import io.github.cmdq.sparrow.server.toObject
import spark.Spark

fun setupListings(service: Sparrow) {
    val dir = "listings"

    Spark.get("/$dir/:id") { request, response ->
        val id = request.params("id").toInt()
        val result = service.listings.getListing(id)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.put("/$dir") { request, response ->
        service.requireAuth(request)
        val listing: Listing = request.body().toObject(service.gson)
        val result = service.listings.editListing(listing)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.put("/$dir/filter") { request, response ->
        service.requireAuth(request)
        val filter: FilterParams = request.body().toObject(service.gson)
        val result = service.listings.getFilteredListings(filter)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.post("/$dir") { request, response ->
        service.requireAuth(request)
        val listing: Listing = request.body().toObject(service.gson)
        val result = service.listings.createListing(listing)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.delete("/$dir/:id") { request, response ->
        service.requireAuth(request)
        val id = request.params("id").toInt()
        val result = service.listings.removeListing(id)
        response.status(result.status)
        result.body.toJson(service.gson)
    }
}