package io.github.cmdq.sparrow.server

import com.google.gson.Gson
import com.google.gson.JsonParseException
import io.github.cmdq.sparrow.server.data.FlatDatastore
import io.github.cmdq.sparrow.server.endpoint.*
import io.github.cmdq.sparrow.server.exception.*
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import spark.Request
import spark.Response
import spark.Spark
import java.io.File

data class Args(
        val port: Int = 9000,
        val fileName: String = "${System.getProperty("user.home")}/.sparrow.json"
)

val defaultArgs = Args()

val options = Options()
        .addOption("p", "port", true, "The port for operation [${defaultArgs.port}]")
        .addOption("f", "file", true, "The persistence file [${defaultArgs.fileName}]")

fun parseArgs(args: Array<String>): Args {
    try {
        // parse options
        val cmd = DefaultParser().parse(options, args)

        // get options
        var result = defaultArgs

        if (cmd.hasOption("p"))
            result = result.copy(port = cmd["p"]!!.toInt())

        if (cmd.hasOption("f"))
            result = result.copy(fileName = cmd["f"]!!)

        // validate options
        if (result.port !in 1025..65535)
            throw IllegalArgumentException()

        if (result.fileName.isBlank())
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
    val gson = Gson()
    val file = File(options.fileName)
    val datastore = FlatDatastore(
            if (file.exists())
                gson.fromJson(file.bufferedReader(), FlatDatastore.Data::class.java)
            else
                FlatDatastore.Data()
    ) {
        if (!file.exists())
            if (!file.createNewFile())
                throw RuntimeException("What the fuck...")
        file.writeText(it.toJson(gson))
    }
    val service = Sparrow(datastore, gson)

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