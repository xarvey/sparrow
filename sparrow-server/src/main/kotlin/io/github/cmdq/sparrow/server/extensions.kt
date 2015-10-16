package io.github.cmdq.sparrow.server

import com.google.gson.Gson
import org.apache.commons.cli.CommandLine

inline fun <reified T : Any> String.toObject(gson: Gson): T = gson.fromJson(this, T::class.java)

fun Any.toJson(gson: Gson): String = gson.toJson(this)

operator fun CommandLine.get(option: String): String? = this.getOptionValue(option)

val String.end: Int
    get() = this.length - 1

val String.length: Int
    get() = this.length()


fun String.isEmail():Boolean {
    val index = this.indexOf('@')
    return index > 0 && index < this.end
}