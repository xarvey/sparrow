package io.github.cmdq.sparrow.server.endpoints

import spark.Spark

fun frontpage() {
    val dir = "frontpage"

    Spark.get("/$dir/borrow/:page") { request, response ->

    }

    Spark.get("/$dir/lend/:page") { request, response ->

    }
}