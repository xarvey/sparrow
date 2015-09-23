package io.github.cmdq.sparrow.server

import io.github.cmdq.sparrow.server.endpoints
import spark.Spark

fun main(args: Array<String>) {
    try {
        val port = getPort(args)
        Spark.port(port)
    } catch (e: SparrowException) {
        System.err.println("Initialization failed: ${e.getMessage()}")
        System.exit(1)
    }

    endpoints.users()
    endpoints.frontpage()
    endpoints.listings()
    endpoints.comments()
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