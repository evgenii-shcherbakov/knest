package com.github.iipekolict.knest.builders

import com.github.iipekolict.knest.annotations.methods.Authentication
import com.github.iipekolict.knest.data.EndpointData
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import kotlin.reflect.KFunction
import kotlin.reflect.full.findAnnotation

class EndpointBuilder(
    private val controller: Any,
    private val route: Route,
    private val endpoint: EndpointData
) {

    private fun authWrapper(block: (route: Route) -> Unit) {
        val authAnnotation: Authentication = endpoint.handler.findAnnotation() ?: return block(route)
        route.authenticate(*authAnnotation.names) { block(this) }
    }

    private fun createEndpointHandler(handler: KFunction<*>): Route.() -> Unit = {
        handle {
            HandlerBuilder(controller, handler, call).build()
        }
    }

    fun build() {
        endpoint.paths.forEach { path ->
            val endpointHandler = createEndpointHandler(endpoint.handler)

            authWrapper { route ->
                if (endpoint.method == null) {
                    HttpMethod.DefaultMethods.forEach {
                        route.route(path, it, endpoint.swaggerCallback, endpointHandler)
                    }
                } else {
                    route.route(path, endpoint.method, endpoint.swaggerCallback, endpointHandler)
                }
            }
        }
    }
}