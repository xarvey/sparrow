package io.github.cmdq.sparrow.server

import com.google.gson.Gson

inline fun <reified T: Any> String.toObject(gson: Gson): T = gson.fromJson(this, T::class.java)

fun Int.toJson(gson: Gson): String = gson.toJson(this)
fun String.toJson(gson: Gson): String = gson.toJson(this)
fun SparrowData.toJson(gson: Gson): String = gson.toJson(this)