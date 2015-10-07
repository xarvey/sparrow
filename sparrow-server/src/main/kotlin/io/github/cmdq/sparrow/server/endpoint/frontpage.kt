package io.github.cmdq.sparrow.server.endpoint

import io.github.cmdq.sparrow.server.Sparrow
import spark.Spark

fun frontpage(service: Sparrow) {
    val dir = "frontpage"

    Spark.get("/$dir/borrow/:page") { request, response ->

    }

    Spark.get("/$dir/lend/:page") { request, response ->

    }
}