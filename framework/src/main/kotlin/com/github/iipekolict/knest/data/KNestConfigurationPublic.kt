package com.github.iipekolict.knest.data

import com.github.iipekolict.knest.configuration.ExceptionConfiguration
import com.github.iipekolict.knest.configuration.FrameworkConfiguration
import io.github.smiley4.ktorswaggerui.SwaggerUIPluginConfig
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.*

class KNestConfigurationPublic {
    private var corsConfiguration: (CORSConfig.() -> Unit)? = null
    private var callLoggingConfiguration: (CallLoggingConfig.() -> Unit) = {}
    private var contentNegotiationConfiguration: (ContentNegotiationConfig.() -> Unit) = {}
    private var swaggerConfiguration: (SwaggerUIPluginConfig.() -> Unit) = {}

    val configuration: KNestConfiguration
        get() = KNestConfiguration(
            corsConfiguration = corsConfiguration,
            callLoggingConfiguration = callLoggingConfiguration,
            contentNegotiationConfiguration = contentNegotiationConfiguration,
            swaggerConfiguration = swaggerConfiguration
        )

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

    fun framework(configuration: FrameworkConfiguration.() -> Unit) {
        FrameworkConfiguration.apply(configuration)
    }
}