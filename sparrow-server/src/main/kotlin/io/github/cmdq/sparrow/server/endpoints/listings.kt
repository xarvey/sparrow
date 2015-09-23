package io.github.cmdq.sparrow.server.endpoints

import io.github.cmdq.sparrow.server.Sparrow
import spark.Spark

fun listings(service: Sparrow) {
    val dir = "listings"

    Spark.get("/$dir") { request, response ->

    }

    Spark.put("/$dir") { request, response ->

    }

    Spark.put("/$dir/borrow/filter") { request, response ->

    }

    Spark.put("/$dir/lend/filter") { request, response ->

    }

    Spark.post("/$dir/borrow") { request, response ->

    }

    Spark.post("/$dir/lend") { request, response ->

    }

    Spark.delete("/$dir/id") { request, response ->

    }
}