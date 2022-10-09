package com.github.iipekolict.knest.builders

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.reflect.*
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.callSuspendBy

class HandlerBuilder(
    private val controller: Any,
    private val handler: KFunction<*>,
    private val call: ApplicationCall,
    private val errorHandler: KFunction<*>?
) {
    private suspend fun getArguments(
        func: KFunction<*>,
        exception: Exception?,
        endpointHandler: KFunction<*>?
    ): Map<KParameter, Any?> {
        return func.parameters.associateWith {
            ArgumentBuilder(it, controller, call, exception, endpointHandler).build()
        }
    }

    private suspend fun executeFunc(
        func: KFunction<*>,
        exception: Exception?,
        endpointHandler: KFunction<*>?
    ) {
        val args: Map<KParameter, Any?> = getArguments(func, exception, endpointHandler)
        val response = func.callSuspendBy(args)

        if (response !== null) {
            call.respond(
                response,
                TypeInfo(response.javaClass::class, response.javaClass::class.java)
            )
        }
    }

    suspend fun build() {
        try {
            executeFunc(handler, null, null)
        } catch (e: Exception) {
            if (errorHandler != null) {
                executeFunc(errorHandler, e, handler)
            } else {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("message" to e.message)
                )
            }
        }
    }
}