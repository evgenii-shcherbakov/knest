package com.github.iipekolict.knest.data

import io.github.smiley4.ktorswaggerui.dsl.OpenApiRoute
import io.ktor.http.*
import kotlin.reflect.KFunction

data class EndpointData(
    val paths: List<String>,
    val method: HttpMethod?,
    val handler: KFunction<*>,
    val swaggerCallback: OpenApiRoute.() -> Unit
)