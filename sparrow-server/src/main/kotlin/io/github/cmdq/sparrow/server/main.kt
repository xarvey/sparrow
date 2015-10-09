package io.github.cmdq.sparrow.server

import com.google.gson.Gson
import com.google.gson.JsonParseException
import io.github.cmdq.sparrow.server.db.SimpleDatastore
import io.github.cmdq.sparrow.server.endpoint.*
import io.github.cmdq.sparrow.server.exception.*
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import spark.Request
import spark.Response
import spark.Spark

data class Args(
        val port: Int = 9000,
        val dbUrl: String = "postgresql://localhost:5432/sparrow",
        val dbUser: String = "sparrow",
        val dbPass: String = ""
)

fun parseArgs(args: Array<String>): Args {
    // define options
    val options = Options()
            .addOption("p", "port", true, "The public port for the server [9000]")
            .addOption("d", "database", true, "The database url [postgresql://localhost:5432/sparrow]")
            .addOption("u", "username", true, "The username to connect to the database [sparrow]")
            .addOption("a", "auth", true, "The password to connect to the database")

    try {
        // parse options
        val cmd = DefaultParser().parse(options, args)

        // get options
        var result = Args()

        if (cmd.hasOption("p"))
            result = result.copy(port = cmd["p"]!!.toInt())

        if (cmd.hasOption("d"))
            result = result.copy(dbUrl = cmd["d"]!!)

        if (cmd.hasOption("u"))
            result = result.copy(dbUser = cmd["u"]!!)

        if (cmd.hasOption("a"))
            result = result.copy(dbPass = cmd["a"]!!)

        // validate options
        if (result.port !in 1025..65535)
            throw IllegalArgumentException()

        if (result.dbUrl.isBlank())
            throw IllegalArgumentException()

        if (cmd.argList.isNotEmpty())
            throw IllegalArgumentException()

        return result
    } catch(e: Exception) {
        HelpFormatter().printHelp("sparrow", options)
        throw SparrowException("Invalid command line arguments")
    }
}

fun main(args: Array<String>) {
    val options: Args = try {
        parseArgs(args)
    } catch (e: Exception) {
        System.exit(1)
        return;
    }

    println(options)

    // val sql2o = Sql2o("jdbc:postgresql://localhost:5432/sparrow", "sparrow", "pass");
    // sql2o.open()

    val datastore = SimpleDatastore()
    val service = Sparrow(datastore)

    setupUsers(service)
    setupFrontpage(service)
    setupListings(service)
    setupComments(service)

    Spark.options("/*") { request, response ->
        val reqHeaders = request.headers("Access-Control-Request-Headers")
        if (reqHeaders != null) response.header("Access-Control-Allow-Headers", reqHeaders)

        val reqMethod = request.headers("Access-Control-Request-Method")
        if (reqHeaders != null) response.header("Access-Control-Allow-Methods", reqMethod)

        ""
    }

    Spark.before { request, response ->
        response.header("Content-type", "application/json")
    }

    CorsFilter.apply()

    Spark.exception(IllegalArgumentException::class.java, ::badRequestHandler)
    Spark.exception(JsonParseException::class.java, ::badRequestHandler)
}

fun badRequestHandler(exception: Exception, request: Request, response: Response) {
    println("Bad request:\n${request.body()}")
    response.status(400)
    val message = exception.getMessage() ?: "Bad request"
    response.body(message.toJson(Gson()))
}