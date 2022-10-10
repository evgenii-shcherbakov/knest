package com.github.iipekolict.knest.data

import com.github.iipekolict.knest.configuration.ExceptionConfiguration
import io.github.smiley4.ktorswaggerui.SwaggerUIPluginConfig
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.*

class KNestConfigurationPublic {
    private var controllers: Set<Any> = emptySet()
    private var corsConfiguration: (CORSConfig.() -> Unit)? = null
    private var callLoggingConfiguration: (CallLoggingConfig.() -> Unit) = {}
    private var contentNegotiationConfiguration: (ContentNegotiationConfig.() -> Unit) = {}
    private var swaggerConfiguration: (SwaggerUIPluginConfig.() -> Unit) = {}

    val configuration: KNestConfiguration
        get() = KNestConfiguration(
            controllers = controllers,
            corsConfiguration = corsConfiguration,
            callLoggingConfiguration = callLoggingConfiguration,
            contentNegotiationConfiguration = contentNegotiationConfiguration,
            swaggerConfiguration = swaggerConfiguration
        )

    fun setControllers(vararg varargControllers: Any) {
        controllers = varargControllers.toSet()
    }

    fun cors(configuration: CORSConfig.() -> Unit) {
        corsConfiguration = configuration
    }

    fun logging(configuration: CallLoggingConfig.() -> Unit) {
        callLoggingConfiguration = configuration
    }

    fun contentNegotiation(configuration: ContentNegotiationConfig.() -> Unit) {
        contentNegotiationConfiguration = configuration
    }

    fun swagger(configuration: SwaggerUIPluginConfig.() -> Unit) {
        swaggerConfiguration = configuration
    }

    fun exceptionHandling(configuration: ExceptionConfiguration.() -> Unit) {
        ExceptionConfiguration.apply(configuration)
    }
}