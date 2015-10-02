package io.github.cmdq.sparrow.server

import spark.Filter
import spark.Request
import spark.Response
import java.util.HashMap
import spark.Spark

/**
 * Really simple helper for enabling CORS in a spark application;
 */
object CorsFilter {

    private val corsHeaders = HashMap<String, String>()

    init {
        corsHeaders.put("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS")
        corsHeaders.put("Access-Control-Allow-Origin", "*")
        corsHeaders.put("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,")
        corsHeaders.put("Access-Control-Allow-Credentials", "true")
    }

    fun apply() {
        val filter = object: Filter {
            override fun handle(request: Request, response: Response) {
                corsHeaders.forEach { key, value ->
                    response.header(key, value)
                }
            }
        }
        Spark.after(filter)
    }
}