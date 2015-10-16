package io.github.cmdq.sparrow.server.endpoint

import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.model.FilterParams
import io.github.cmdq.sparrow.server.model.ListingType
import io.github.cmdq.sparrow.server.toJson
import spark.Spark

fun setupFrontpage(service: Sparrow) {
    val dir = "frontpage"

    Spark.get("/$dir/borrow/:page") { request, response ->
        val page = request.params("page").toInt()
        val result = service.frontpage.getBorrowPage(page)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.get("/$dir/lend/:page") { request, response ->
        val page = request.params("page").toInt()
        val result = service.frontpage.getLendingPage(page)
        response.status(result.status)
        result.body.toJson(service.gson)
    }
}