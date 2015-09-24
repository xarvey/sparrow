package io.github.cmdq.sparrow.server

import com.google.gson.Gson
import com.google.gson.JsonParseException
import io.github.cmdq.sparrow.server.endpoints
import spark.Request
import spark.Response
import spark.Spark

fun main(args: Array<String>) {
    try {
        val port = getPort(args)
        Spark.port(port)
    } catch (e: SparrowException) {
        System.err.println("Initialization failed: ${e.getMessage()}")
        System.exit(1)
    }

    val datastore = MockDatastore()
    val service = Sparrow(datastore)

    endpoints.users(service)
    endpoints.frontpage(service)
    endpoints.listings(service)
    endpoints.comments(service)

    Spark.after { request, response ->
        response.header("Content-type", "application/json")
    }

    Spark.exception(IllegalArgumentException::class.java, ::badRequestHandler)
    Spark.exception(JsonParseException::class.java, ::badRequestHandler)
}

fun badRequestHandler(exception: Exception, request: Request, response: Response) {
    println("Bad request:\n${request.body()}")
    response.status(400)
    val message = exception.getMessage() ?: "Bad request"
    response.body(message.toJson(Gson()))
}

fun getPort(args: Array<String>): Int {
    if (args.count() != 1)
        throw SparrowException("You must provide one argument for the port, found ${args.count()} arguments")

    val port: Int
    try {
        port = args[0].toInt()
    } catch (e: NumberFormatException) {
        throw SparrowException("Port must be an integer, found ${args[0]}");
    }

    if (port !in 1025..65535)
        throw SparrowException("Port must be within range [1025-65535], found $port")

    return port
}