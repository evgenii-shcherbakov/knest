package com.github.iipekolict.knest.builders

import com.github.iipekolict.knest.data.Endpoint
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.server.application.*
import io.ktor.server.routing.*
import kotlin.reflect.KFunction

class EndpointBuilder(
    private val controller: Any,
    private val route: Route,
    private val endpoint: Endpoint,
    private val errorHandler: KFunction<*>?
) {

    private fun createEndpointHandler(handler: KFunction<*>): Route.() -> Unit = {
        handle {
            HandlerBuilder(controller, handler, call, errorHandler).build()
        }
    }

    fun build() {
        endpoint.paths.map { path ->
            val endpointHandler = createEndpointHandler(endpoint.handler)

            if (endpoint.method == null) {
                route.route(path, endpoint.swaggerCallback, endpointHandler)
            } else {
                route.route(path, endpoint.method, endpoint.swaggerCallback, endpointHandler)
            }
        }
    }
}