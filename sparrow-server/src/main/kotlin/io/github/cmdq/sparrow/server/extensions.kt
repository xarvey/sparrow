package io.github.cmdq.sparrow.server

import com.google.gson.Gson
import org.apache.commons.cli.CommandLine

inline fun <reified T : Any> String.toObject(gson: Gson): T = gson.fromJson(this, T::class.java)

fun Any.toJson(gson: Gson): String = gson.toJson(this)

operator fun CommandLine.get(option: String): String? = this.getOptionValue(option)

val String.end: Int
    get() = this.length - 1

fun String.isEmail(): Boolean = with(indexOf('@')) { this > 0 && this < end }
fun String.isZipCode(): Boolean = this.count() == 5 && this.fold(true) { r, c -> r && c.isDigit() }

val String.hashString: String
    get() = this.hashCode().toString()