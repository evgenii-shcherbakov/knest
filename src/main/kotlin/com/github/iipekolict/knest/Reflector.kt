package com.github.iipekolict.knest

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.reflect.*
import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.*
import com.github.iipekolict.knest.data.Endpoint
import java.math.BigDecimal
import kotlin.reflect.KFunction
import kotlin.reflect.full.callSuspendBy
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType
import kotlin.reflect.jvm.javaType

class Reflector(private val routing: Routing) {

    private fun convertAnnotation(
        annotationMethod: HttpMethod?,
        annotationHandler: KFunction<*>,
        annotationPaths: Array<String>,
        annotationPath: String,
        annotationDescription: String,
        annotationSummary: String,
        annotationTags: Array<String>,
        annotationOperationId: String,
        annotationSecuritySchemeName: String,
    ): Endpoint {
        val endpointPaths: Array<String> = if (annotationPaths.isNotEmpty()) {
            annotationPaths
        } else {
            arrayOf(annotationPath)
        }

        return Endpoint(
            paths = endpointPaths,
            method = annotationMethod,
            handler = annotationHandler,
            swaggerCallback = {
                description = annotationDescription.ifEmpty { null }
                summary = annotationSummary.ifEmpty { null }
                operationId = annotationOperationId.ifEmpty { null }
                securitySchemeName = annotationSecuritySchemeName.ifEmpty { null }

                if (annotationTags.isNotEmpty()) {
                    tags = annotationTags.toList()
                }
            }
        )
    }

    private fun getHandlers(obj: Any): List<Endpoint> {
        return obj::class.declaredMemberFunctions.map { method ->
            method.annotations.mapNotNull {
                when (it) {
                    is Get -> convertAnnotation(
                        HttpMethod.Get,
                        method,
                        it.paths,
                        it.path,
                        it.description,
                        it.summary,
                        it.tags,
                        it.operationId,
                        it.securitySchemeName
                    )
                    is Post -> convertAnnotation(
                        HttpMethod.Post,
                        method,
                        it.paths,
                        it.path,
                        it.description,
                        it.summary,
                        it.tags,
                        it.operationId,
                        it.securitySchemeName
                    )
                    is Put -> convertAnnotation(
                        HttpMethod.Put,
                        method,
                        it.paths,
                        it.path,
                        it.description,
                        it.summary,
                        it.tags,
                        it.operationId,
                        it.securitySchemeName
                    )
                    is Patch -> convertAnnotation(
                        HttpMethod.Patch,
                        method,
                        it.paths,
                        it.path,
                        it.description,
                        it.summary,
                        it.tags,
                        it.operationId,
                        it.securitySchemeName
                    )
                    is Delete -> convertAnnotation(
                        HttpMethod.Delete,
                        method,
                        it.paths,
                        it.path,
                        it.description,
                        it.summary,
                        it.tags,
                        it.operationId,
                        it.securitySchemeName
                    )
                    is Head -> convertAnnotation(
                        HttpMethod.Head,
                        method,
                        it.paths,
                        it.path,
                        it.description,
                        it.summary,
                        it.tags,
                        it.operationId,
                        it.securitySchemeName
                    )
                    is Options -> convertAnnotation(
                        HttpMethod.Options,
                        method,
                        it.paths,
                        it.path,
                        it.description,
                        it.summary,
                        it.tags,
                        it.operationId,
                        it.securitySchemeName
                    )
                    is All -> convertAnnotation(
                        null,
                        method,
                        it.paths,
                        it.path,
                        it.description,
                        it.summary,
                        it.tags,
                        it.operationId,
                        it.securitySchemeName
                    )
                    else -> null
                }
            }
        }.flatten()
    }

    fun injectControllers(controllers: Iterable<Any>) {
        controllers.map {
            handleController(it)
        }
    }

    private fun handleController(obj: Any) {
        try {
            val controllerAnnotation = obj.javaClass.getDeclaredAnnotation(Controller::class.java)

            val controllerSwaggerCallback: OpenApiRoute.() -> Unit = {
                description = controllerAnnotation.description.ifEmpty { null }
                summary = controllerAnnotation.summary.ifEmpty { null }
                operationId = controllerAnnotation.operationId.ifEmpty { null }
                securitySchemeName = controllerAnnotation.securitySchemeName.ifEmpty { null }

                if (controllerAnnotation.tags.isNotEmpty()) {
                    tags = controllerAnnotation.tags.toList()
                }
            }

            routing.route(controllerAnnotation.path, controllerSwaggerCallback) {
                getHandlers(obj).forEach { endpoint ->
                    endpoint.paths.forEach { path ->
                        val endpointHandler = createEndpointHandler(obj, endpoint.handler)

                        if (endpoint.method == null) {
                            this.route(path, endpoint.swaggerCallback, endpointHandler)
                        } else {
                            this.route(path, endpoint.method, endpoint.swaggerCallback, endpointHandler)
                        }
                    }
                }
            }
        } catch (_: Exception) {
            throw RuntimeException("Controller should be annotated by Controller annotation")
        }
    }

    private fun createEndpointHandler(obj: Any, handler: KFunction<*>): Route.() -> Unit = {
        handle {
            handleEndpoint(obj, handler, call)
        }
    }

    private suspend fun handleEndpoint(
        obj: Any,
        handler: KFunction<*>,
        call: ApplicationCall
    ) {
        val parameters = handler.parameters.mapNotNull {
            when {
                it.type.javaType == obj.javaClass -> it to obj
                it.type.javaType == ApplicationCall::class.java -> it to call
                it.type.javaType == HttpMethod::class.java -> it to call.request.local.method
                call.parameters.contains(it.name ?: "") -> it to call.parameters[it.name ?: ""]
                else -> null
            }
        }.toMap()

        when(val response = handler.callSuspendBy(parameters)) {
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