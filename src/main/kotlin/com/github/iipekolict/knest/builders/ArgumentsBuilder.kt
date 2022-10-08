package com.github.iipekolict.knest.builders

import io.ktor.http.*
import io.ktor.server.application.*
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.jvm.javaType

class ArgumentsBuilder(
    private val controller: Any,
    private val handler: KFunction<*>,
    private val call: ApplicationCall
) {

    private fun convertParameter(parameter: KParameter): Any? {
        return when {
            parameter.type.javaType == controller.javaClass -> parameter to controller
            parameter.type.javaType == ApplicationCall::class.java -> parameter to call
            parameter.type.javaType == HttpMethod::class.java -> parameter to call.request.local.method
            call.parameters.contains(parameter.name ?: "") -> parameter to call.parameters[parameter.name ?: ""]
            else -> null
        }
    }

    fun build(): Map<KParameter, Any?> {
        return handler.parameters.associateWith {
            convertParameter(it)
        }
    }
}