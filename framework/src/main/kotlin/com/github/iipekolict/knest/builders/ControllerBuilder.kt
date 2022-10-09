package com.github.iipekolict.knest.builders

import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.*
import com.github.iipekolict.knest.data.Endpoint
import com.github.iipekolict.knest.exceptions.KNestException
import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.routing.*
import kotlin.reflect.full.declaredMemberFunctions

class ControllerBuilder(private val routing: Routing, private val controller: Any) {

    private val controllerAnnotation: Controller
        get() = controller.javaClass.getDeclaredAnnotation(Controller::class.java)
            ?: throw KNestException("Controller should be annotated by Controller annotation")

    private val controllerSwaggerCallback: OpenApiRoute.() -> Unit
        get() = {
            description = controllerAnnotation.description.ifEmpty { null }
            summary = controllerAnnotation.summary.ifEmpty { null }
            operationId = controllerAnnotation.operationId.ifEmpty { null }
            securitySchemeName = controllerAnnotation.securitySchemeName.ifEmpty { null }

            if (controllerAnnotation.tags.isNotEmpty()) {
                tags = controllerAnnotation.tags.toList()
            }
        }

    private val endpoints: List<Endpoint>
        get() {
            return controller::class.declaredMemberFunctions.map { method ->
                method.annotations.mapNotNull {
                    when (it) {
                        is Get -> Endpoint.fromAnnotation(
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
                        is Post -> Endpoint.fromAnnotation(
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
                        is Put -> Endpoint.fromAnnotation(
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
                        is Patch -> Endpoint.fromAnnotation(
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
                        is Delete -> Endpoint.fromAnnotation(
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
                        is Head -> Endpoint.fromAnnotation(
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
                        is Options -> Endpoint.fromAnnotation(
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
                        is All -> Endpoint.fromAnnotation(
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

    fun build() {
        routing.route(controllerAnnotation.path, controllerSwaggerCallback) {
            endpoints.map {
                EndpointBuilder(controller, this, it).build()
            }
        }
    }
}