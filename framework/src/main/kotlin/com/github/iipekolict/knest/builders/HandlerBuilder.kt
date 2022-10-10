package com.github.iipekolict.knest.builders

import com.github.iipekolict.knest.annotations.methods.DefaultExceptionHandler
import com.github.iipekolict.knest.annotations.methods.ExceptionHandler
import com.github.iipekolict.knest.configuration.ExceptionConfiguration
import com.github.iipekolict.knest.exceptions.KNestException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.reflect.*
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.callSuspendBy
import kotlin.reflect.full.findAnnotation

class HandlerBuilder(
    private val controller: Any,
    private val handler: KFunction<*>,
    private val call: ApplicationCall
) {

    private suspend fun getArguments(
        func: KFunction<*>,
        container: Any?,
        exception: Exception?,
        endpointHandler: KFunction<*>?
    ): Map<KParameter, Any?> {
        return func.parameters.associateWith {
            ArgumentBuilder(it, container ?: controller, call, exception, endpointHandler).build()
        }
    }

    private suspend fun executeFunc(
        func: KFunction<*>,
        container: Any?,
        exception: Exception?,
        endpointHandler: KFunction<*>?
    ) {
        val args: Map<KParameter, Any?> = getArguments(func, container, exception, endpointHandler)
        val response = func.callSuspendBy(args)

        if (response !== null) {
            call.respond(
                response,
                TypeInfo(response.javaClass::class, response.javaClass::class.java)
            )
        }
    }

    private suspend fun checkExceptionHandler(
        exception: Exception,
        exceptionHandler: ExceptionConfiguration.Handler
    ): Boolean? {
        val isDefaultHandler: Boolean = exceptionHandler.handler.annotations.any {
            it is DefaultExceptionHandler
        }

        return if (!isDefaultHandler) {
            val excAnnotation = exceptionHandler.handler.findAnnotation<ExceptionHandler>()
                ?: throw KNestException(
                    "Not provided 'ExceptionHandler' annotation for exception handler"
                )

            if (exception.instanceOf(excAnnotation.type)) {
                executeFunc(exceptionHandler.handler, exceptionHandler.container, exception, handler)
                true
            } else {
                null
            }
        } else {
            executeFunc(exceptionHandler.handler, exceptionHandler.container, exception, handler)
            true
        }
    }

    private suspend fun handleException(exception: Exception) {
        val exc = exception.cause as? Exception? ?: exception

        val handlersResult = ExceptionConfiguration.configuration.sortedHandlers.firstNotNullOfOrNull {
            checkExceptionHandler(exc, it)
        }

        if (handlersResult == null) {
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("message" to exc.message)
            )
        }
    }

    suspend fun build() {
        try {
            executeFunc(handler, controller, null, null)
        } catch (e: Exception) {
            handleException(e)
        }
    }
}