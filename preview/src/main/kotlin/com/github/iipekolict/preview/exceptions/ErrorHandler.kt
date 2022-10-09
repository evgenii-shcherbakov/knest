package com.github.iipekolict.preview.exceptions

import com.github.iipekolict.knest.annotations.properties.Call
import com.github.iipekolict.knest.annotations.properties.Method
import com.github.iipekolict.knest.exceptions.KNestException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.lang.Exception

suspend fun errorHandler(
    exception: Exception,
    @Call call: ApplicationCall,
    @Method method: HttpMethod
) {
    when (exception) {
        is KNestException -> call.respond(
            HttpStatusCode.InternalServerError,
            mapOf(
                "method" to method.value,
                "code" to "framework-error",
                "message" to exception.message,
            )
        )
        else -> call.respond(
            HttpStatusCode.InternalServerError,
            mapOf(
                "method" to method.value,
                "code" to "unknown-error",
                "message" to exception.message,
            )
        )
    }
}