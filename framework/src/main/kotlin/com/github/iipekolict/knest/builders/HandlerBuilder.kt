package com.github.iipekolict.knest.builders

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.reflect.*
import java.math.BigDecimal
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.callSuspendBy
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class HandlerBuilder(
    private val controller: Any,
    private val handler: KFunction<*>,
    private val call: ApplicationCall
) {
    private val arguments: Map<KParameter, Any?>
        get() = ArgumentsBuilder(controller, handler, call).build()

    suspend fun build() {
        when(val response = handler.callSuspendBy(arguments)) {
            is Int, is Double, is Float, is BigDecimal -> call.respond(response)
            is String -> call.respondText(response)
            is Map<*, *> -> call.respond(response)
            is Set<*> -> call.respond(response)
            is Iterable<*>, is Array<*> -> call.respond(response)
            instanceOf(JvmType.Object::class) -> call.respond(response)
            else -> {}
        }
    }
}