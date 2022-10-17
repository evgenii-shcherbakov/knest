package com.github.iipekolict.knest.builders

import com.github.iipekolict.knest.data.EndpointData
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlin.reflect.KFunction

class EndpointBuilder(
    private val controller: Any,
    private val route: Route,
    private val endpoint: EndpointData
) {

    private fun createEndpointHandler(handler: KFunction<*>): Route.() -> Unit = {
        handle {
            HandlerBuilder(controller, handler, call).build()
        }
    }

    fun build() {
        endpoint.paths.forEach { path ->
            val endpointHandler = createEndpointHandler(endpoint.handler)

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