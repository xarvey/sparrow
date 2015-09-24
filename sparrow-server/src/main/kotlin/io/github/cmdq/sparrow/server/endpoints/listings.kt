package io.github.cmdq.sparrow.server.endpoints

import com.google.gson.Gson
import io.github.cmdq.sparrow.server.Listing
import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.toJson
import io.github.cmdq.sparrow.server.toObject
import spark.Spark

fun listings(service: Sparrow) {
    val gson = Gson()
    val dir = "listings"

    Spark.get("/$dir/:id") { request, response ->
        val id = request.params("id").toInt()
        val result = service.listings.getListing(id)
        response.status(result.status)
        result.body.toJson(gson)
    }

    Spark.put("/$dir") { request, response ->
        val listing: Listing = request.body().toObject(gson)
        val result = service.listings.editListing(listing)
        response.status(result.status)
        result.body.toJson(gson)
    }

    Spark.put("/$dir/borrow/filter") { request, response ->

    }

    Spark.put("/$dir/lend/filter") { request, response ->

    }

    Spark.post("/$dir/borrow") { request, response ->

    }

    Spark.post("/$dir/lend") { request, response ->
        val listing: Listing = request.body().toObject(gson)
        val result = service.listings.createLendListing(listing)
        response.status(result.status)
        result.body.toJson(gson)
    }

    Spark.delete("/$dir/id") { request, response ->

    }
}