package io.github.cmdq.sparrow.server.endpoints

import spark.Spark

fun users() {
    val dir = "users"

    Spark.get("/$dir/:id") { request, response ->

    }

    Spark.get("/$dir") { request, response ->

    }

    Spark.post("/$dir") { request, response ->

    }

    Spark.put("/$dir") { request, response ->

    }

    Spark.get("/$dir/friends/add/:id") { request, response ->

    }

    Spark.get("/$dir/friends/remove/:id") { request, response ->

    }
}