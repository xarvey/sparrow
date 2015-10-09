package io.github.cmdq.sparrow.server

import com.google.gson.Gson
import com.google.gson.JsonParseException
import io.github.cmdq.sparrow.server.db.SqlDatastore
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

val defaultArgs = Args()

val options = Options()
        .addOption("p", "port", true, "The public port for the server [${defaultArgs.port}]")
        .addOption("d", "database", true, "The database url [${defaultArgs.dbUrl}]")
        .addOption("u", "username", true, "The username to connect to the database [${defaultArgs.dbUser}]")
        .addOption("a", "auth", true, "The password to connect to the database [${defaultArgs.dbPass}]")

fun parseArgs(args: Array<String>): Args {
    try {
        // parse options
        val cmd = DefaultParser().parse(options, args)

        // get options
        var result = defaultArgs

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
        throw SparrowException("Invalid command line arguments")
    }
}

fun main(args: Array<String>) {
    // get cli args
    val options: Args = try {
        parseArgs(args)
    } catch (e: Exception) {
        HelpFormatter().printHelp("sparrow", options)
        System.exit(1)
        return;
    }

    // init service
    val datastore = SqlDatastore(options.dbUrl, options.dbUser, options.dbPass)
    val service = Sparrow(datastore)

    // set service port
    Spark.port(options.port)

    // setup service endpoints
    setupUsers(service)
    setupFrontpage(service)
    setupListings(service)
    setupComments(service)

    // set global content type
    Spark.before { request, response ->
        response.header("Content-type", "application/json")
    }

    // apply CORS filter
    CorsFilter.apply()

    // set bad request exception handlers
    listOf(
            IllegalArgumentException::class.java,
            JsonParseException::class.java
    ).forEach {
        Spark.exception(it) { exception: Exception, request: Request, response: Response ->
            println("Bad request:\n${request.body()}")
            response.status(400)
            val message = exception.getMessage() ?: "Bad request"
            response.body(message.toJson(Gson()))
        }
    }
}