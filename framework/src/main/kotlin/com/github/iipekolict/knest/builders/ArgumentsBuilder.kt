package com.github.iipekolict.knest.builders

import com.github.iipekolict.knest.annotations.properties.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaType

class ArgumentsBuilder(
    private val controller: Any,
    private val handler: KFunction<*>,
    private val call: ApplicationCall
) {
    private fun convertParameterByType(parameter: KParameter): Any? {
        return when (parameter.type.javaType) {
            controller.javaClass -> controller
            ApplicationCall::class.java -> call
            HttpMethod::class.java -> call.request.local.method
            else -> null
        }
    }

    private fun pipeParameter(parameter: KParameter, value: Any?): Any? {
        try {
            if (value == null) return value

            return when (parameter.type.javaType) {
                String::class.java -> {
                    if (value is String) value else value.toString()
                }
                Int::class.java -> {
                    when (value) {
                        is String -> value.toString().toInt()
                        is Int -> value
                        is Float -> value.toInt()
                        is Double -> value.toInt()
                        else -> value.toString().toInt()
                    }
                }
                Float::class.java -> {
                    when (value) {
                        is String -> value.toFloat()
                        is Int -> value.toFloat()
                        is Float -> value
                        is Double -> value.toFloat()
                        else -> value.toString().toFloat()
                    }
                }
                Double::class.java -> {
                    when (value) {
                        is String -> value.toDouble()
                        is Int -> value.toDouble()
                        is Float -> value.toDouble()
                        is Double -> value
                        else -> value.toString().toFloat()
                    }
                }
                else -> value
            }
        } catch (e: Exception) {
            throw RuntimeException("Can't convert parameter to provided type")
        }
    }

    private suspend fun convertParameterByAnnotation(parameter: KParameter): Any? {
        val isCall: Boolean = parameter.findAnnotation<Call>() != null
        val param: Param? = parameter.findAnnotation()
        val query: Query? = parameter.findAnnotation()
        val header: Header? = parameter.findAnnotation()
        val body: Body? = parameter.findAnnotation()

        return when {
            isCall -> {
                call
            }
            param?.name?.isNotBlank() == true -> {
                pipeParameter(parameter, call.parameters[param.name])
            }
            param?.name?.isBlank() == true -> {
                call.parameters
            }
            query?.name?.isNotBlank() == true -> {
                pipeParameter(parameter, call.request.queryParameters[query.name])
            }
            query?.name?.isBlank() == true -> {
                call.request.queryParameters
            }
            header?.name?.isNotBlank() == true -> {
                pipeParameter(parameter, call.request.headers[header.name])
            }
            header?.name?.isBlank() == true -> {
                call.request.headers
            }
            body?.name?.isNotBlank() == true -> {
                pipeParameter(parameter, call.receive<Map<String, *>>()[body.name])
            }
            body?.name?.isBlank() == true -> {
                call.receive(body.type)
            }
            else -> null
        }
    }

    suspend fun build(): Map<KParameter, Any?> {
        return handler.parameters.associateWith {
            convertParameterByAnnotation(it) ?: convertParameterByType(it)
        }
    }
}