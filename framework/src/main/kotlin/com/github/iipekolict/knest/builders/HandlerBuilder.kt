package com.github.iipekolict.knest.builders

import com.github.iipekolict.knest.annotations.methods.DefaultExceptionHandler
import com.github.iipekolict.knest.annotations.methods.ExceptionHandler
import com.github.iipekolict.knest.annotations.methods.Middleware
import com.github.iipekolict.knest.configuration.modular.ExceptionConfiguration
import com.github.iipekolict.knest.configuration.modular.MiddlewareConfiguration
import com.github.iipekolict.knest.data.HandlerData
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
        endpointHandler: KFunction<*>?,
        middlewareAnnotation: Annotation?
    ): Map<KParameter, Any?> {
        return func.parameters.associateWith {
            ArgumentBuilder(
                parameter = it,
                controller = container ?: controller,
                call = call,
                exception = exception,
                func = endpointHandler,
                middlewareAnnotation = middlewareAnnotation
            )
                .build()
        }
    }

    private suspend fun executeFunc(
        func: KFunction<*>,
        container: Any?,
        exception: Exception?,
        endpointHandler: KFunction<*>?,
        middlewareAnnotation: Annotation?
    ): Boolean? {
        val args: Map<KParameter, Any?> = getArguments(
            func,
            container,
            exception,
            endpointHandler,
            middlewareAnnotation
        )

        val response = func.callSuspendBy(args)

        if (response != null) {
            call.respond(
                response,
                TypeInfo(response.javaClass::class, response.javaClass::class.java)
            )

            return true
        }

        return null
    }

    private suspend fun executeFunc(
        func: KFunction<*>,
        container: Any?,
        endpointHandler: KFunction<*>?,
        middlewareAnnotation: Annotation?
    ): Boolean? {
        return executeFunc(func, container, null, endpointHandler, middlewareAnnotation)
    }

    private suspend fun checkExceptionHandler(
        exception: Exception,
        exceptionHandler: HandlerData
    ): Boolean? {
        val isDefaultHandler: Boolean = exceptionHandler.handler.annotations.any {
            it is DefaultExceptionHandler
        }

        if (isDefaultHandler) {
            return executeFunc(
                func = exceptionHandler.handler,
                container = exceptionHandler.container,
                exception = exception,
                endpointHandler = handler,
                middlewareAnnotation = null
            )
        }

        val excAnnotation = exceptionHandler.handler.findAnnotation<ExceptionHandler>()
            ?: throw KNestException(
                "Not provided 'ExceptionHandler' annotation for exception handler"
            )

        if (exception.instanceOf(excAnnotation.type)) {
            return executeFunc(
                func = exceptionHandler.handler,
                container = exceptionHandler.container,
                exception = exception,
                endpointHandler = handler,
                middlewareAnnotation = null
            )
        }

        return null
    }

    private suspend fun handleException(exception: Exception) {
        val exc = exception.cause as? Exception? ?: exception
        val exceptionHandlers = ExceptionConfiguration.configuration.sortedHandlers

        if (exceptionHandlers.firstNotNullOfOrNull { checkExceptionHandler(exc, it) } == null) {
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = mapOf("message" to exc.message)
            )
        }
    }

    private suspend fun handleMiddleware(middleware: HandlerData): Boolean? {
        val middlewareAnnotation: Middleware = middleware.handler.findAnnotation()
            ?: throw KNestException("Middleware handler should be annotated with 'Middleware' annotation")

        val matchedAnnotation: Annotation? = handler.annotations.find {
            it.instanceOf(middlewareAnnotation.annotation)
        }

        if (matchedAnnotation != null) {
            return executeFunc(
                func = middleware.handler,
                container = middleware.container,
                endpointHandler = handler,
                middlewareAnnotation = matchedAnnotation
            )
        }

        return null
    }

    suspend fun build() {
        try {
            val middlewares: Set<HandlerData> = MiddlewareConfiguration.configuration.middlewares

            if (middlewares.firstNotNullOfOrNull { handleMiddleware(it) } == null) {
                executeFunc(handler, controller, handler, null)
            }
        } catch (e: Exception) {
            handleException(e)
        }
    }
}