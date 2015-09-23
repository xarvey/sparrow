package io.github.cmdq.sparrow.server.endpoints

import spark.Spark

fun comments() {
    val dir = "comments"

    Spark.get("/$dir/:id") { request, response ->

    }

    Spark.post("/$dir/listing/:id") { request, response ->

    }

    Spark.post("/$dir/user/:id") { request, response ->

    }

    Spark.delete("/$dir/:id") { request, response ->

    }
}