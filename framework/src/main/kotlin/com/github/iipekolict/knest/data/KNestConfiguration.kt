package com.github.iipekolict.knest.data

import io.github.smiley4.ktorswaggerui.SwaggerUIPluginConfig
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.*
import kotlin.reflect.KFunction

class KNestConfiguration(
    val controllers: Set<Any>,
    val corsConfiguration: (CORSConfig.() -> Unit)?,
    val callLoggingConfiguration: (CallLoggingConfig.() -> Unit),
    val contentNegotiationConfiguration: (ContentNegotiationConfig.() -> Unit),
    val swaggerConfiguration: (SwaggerUIPluginConfig.() -> Unit),
    var errorHandler: KFunction<*>?
)