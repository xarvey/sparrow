package io.github.cmdq.sparrow.server.endpoints

import io.github.cmdq.sparrow.server.Sparrow
import spark.Spark

fun comments(service: Sparrow) {
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