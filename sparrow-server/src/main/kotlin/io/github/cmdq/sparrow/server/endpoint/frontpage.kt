package io.github.cmdq.sparrow.server.endpoint

import io.github.cmdq.sparrow.server.Sparrow
import io.github.cmdq.sparrow.server.model.ListingType
import io.github.cmdq.sparrow.server.toJson
import spark.Spark

fun setupFrontpage(service: Sparrow) {
    val dir = "frontpage"
    val pageSize = 25

    Spark.get("/$dir/borrow/:page") { request, response ->
        val page = request.params("page").toInt()
        val result = service.frontpage.getPage(ListingType.borrow, page, pageSize)
        response.status(result.status)
        result.body.toJson(service.gson)
    }

    Spark.get("/$dir/lend/:page") { request, response ->
        val page = request.params("page").toInt()
        val result = service.frontpage.getPage(ListingType.lend, page, pageSize)
        response.status(result.status)
        result.body.toJson(service.gson)
    }
}