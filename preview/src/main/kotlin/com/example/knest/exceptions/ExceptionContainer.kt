package com.example.knest.exceptions

import io.github.evgenii_shcherbakov.knest.annotations.methods.DefaultExceptionHandler
import io.github.evgenii_shcherbakov.knest.annotations.methods.ExceptionHandler
import io.github.evgenii_shcherbakov.knest.annotations.properties.Call
import io.github.evgenii_shcherbakov.knest.annotations.properties.Exc
import io.github.evgenii_shcherbakov.knest.annotations.properties.Handler
import io.github.evgenii_shcherbakov.knest.annotations.properties.Method
import io.github.evgenii_shcherbakov.knest.exceptions.KNestException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.lang.Exception
import kotlin.reflect.KFunction

object ExceptionContainer {

    @ExceptionHandler(KNestException::class)
    suspend fun handleKNestException(
        @Exc exception: Exception,
        @Call call: ApplicationCall,
        @Method method: HttpMethod,
        @Handler handler: KFunction<*>?
    ) {
        call.respond(
            HttpStatusCode.InternalServerError,
            mapOf(
                "method" to method.value,
                "code" to "framework-error",
                "message" to exception.message,
                "handlerName" to handler?.name
            )
        )
    }

    @DefaultExceptionHandler
    suspend fun defaultExceptionHandler(
        @Exc exception: Exception,
        @Call call: ApplicationCall,
        @Method method: HttpMethod,
        @Handler handler: KFunction<*>?
    ) {
        call.respond(
            HttpStatusCode.InternalServerError,
            mapOf(
                "method" to method.value,
                "code" to "unknown-error",
                "message" to exception.message,
                "handlerName" to handler?.name
            )
        )
    }
}