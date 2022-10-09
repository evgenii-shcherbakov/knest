package com.github.iipekolict.knest.builders

import com.github.iipekolict.knest.annotations.properties.*
import com.github.iipekolict.knest.exceptions.KNestException
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.javaType

class ArgumentsBuilder(
    private val controller: Any,
    private val handler: KFunction<*>,
    private val call: ApplicationCall
) {
    private suspend fun convertParameterByType(parameter: KParameter): Any? {
        return when (parameter.type.javaType) {
            controller.javaClass -> controller
            ApplicationCall::class.java -> call
            HttpMethod::class.java -> call.request.local.method
            MultiPartData::class.java -> call.receiveMultipart()
            ApplicationRequest::class.java -> call.request
            ApplicationResponse::class.java -> call.response
            Headers::class.java -> call.request.headers
            RequestCookies::class.java -> call.request.cookies
            ResponseCookies::class.java -> call.response.cookies
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
            throw KNestException("Can't convert parameter to provided type")
        }
    }

    private suspend fun convertParameterByAnnotation(parameter: KParameter): Any? {
        val isCall: Boolean = parameter.findAnnotation<Call>() != null
        val param: Param? = parameter.findAnnotation()
        val query: Query? = parameter.findAnnotation()
        val header: Header? = parameter.findAnnotation()
        val body: Body? = parameter.findAnnotation()
        val cookies: Cookies? = parameter.findAnnotation()
        val isHost: Boolean = parameter.findAnnotation<Host>() != null
        val isIp: Boolean = parameter.findAnnotation<Ip>() != null
        val file: File? = parameter.findAnnotation()
        val isReq: Boolean = parameter.findAnnotation<Req>() != null
        val isRes: Boolean = parameter.findAnnotation<Res>() != null
        val isMethod: Boolean = parameter.findAnnotation<Method>() != null

        return when {
            isCall -> {
                call
            }
            param != null -> {
                if (param.name.isNotBlank()) {
                    pipeParameter(parameter, call.parameters[param.name])
                } else {
                    call.parameters
                }
            }
            query != null -> {
                if (query.name.isNotBlank()) {
                    pipeParameter(parameter, call.request.queryParameters[query.name])
                } else {
                    call.request.queryParameters
                }
            }
            header != null -> {
                if (header.name.isNotBlank()) {
                    pipeParameter(parameter, call.request.headers[header.name])
                } else {
                    call.request.headers
                }
            }
            body != null -> {
                if (body.name.isNotBlank()) {
                    pipeParameter(parameter, call.receive<Map<String, *>>()[body.name])
                } else {
                    call.receive(body.type)
                }
            }
            cookies != null -> {
                if (cookies.name.isNotBlank()) {
                    pipeParameter(parameter, call.request.cookies[cookies.name])
                } else {
                    call.request.cookies
                }
            }
            isHost -> {
                call.request.host()
            }
            isIp -> {
                call.request.origin.remoteHost
            }
            file != null -> {
                if (file.name.isNotBlank()) {
                    var fileItem: PartData.FileItem? = null

                    call.receiveMultipart().forEachPart {
                        if (it is PartData.FileItem && it.name == file.name) {
                            fileItem = it
                        }
                    }

                    fileItem
                } else {
                    val fileItems = mutableSetOf<PartData.FileItem>()

                    call.receiveMultipart().forEachPart {
                        if (it is PartData.FileItem) {
                            fileItems.add(it)
                        }
                    }

                    fileItems.toSet()
                }
            }
            isReq -> {
                call.request
            }
            isRes -> {
                call.response
            }
            isMethod -> {
                call.request.local.method
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