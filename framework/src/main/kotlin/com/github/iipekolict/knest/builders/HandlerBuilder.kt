package com.github.iipekolict.knest.builders

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.reflect.*
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.callSuspendBy

class HandlerBuilder(
    private val controller: Any,
    private val handler: KFunction<*>,
    private val call: ApplicationCall
) {
    private suspend fun getArguments(): Map<KParameter, Any?> {
        return ArgumentsBuilder(controller, handler, call).build()
    }

    suspend fun build() {
        try {
            val response = handler.callSuspendBy(getArguments())

            if (response !== null) {
                call.respond(
                    response,
                    TypeInfo(response.javaClass::class, response.javaClass::class.java)
                )
            }
        } catch (e: Exception) {
            call.respond("Error!") // TODO: implement custom error handling logic later
        }
    }
}