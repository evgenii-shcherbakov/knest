package com.github.iipekolict.knest.configuration

import com.github.iipekolict.knest.configuration.modular.ExceptionConfiguration
import com.github.iipekolict.knest.configuration.modular.FrameworkConfiguration
import com.github.iipekolict.knest.configuration.plugin.CallLoggingConfiguration
import com.github.iipekolict.knest.configuration.plugin.ContentNegotiationConfiguration
import com.github.iipekolict.knest.configuration.plugin.CorsConfiguration
import com.github.iipekolict.knest.configuration.plugin.SwaggerConfiguration
import io.github.smiley4.ktorswaggerui.SwaggerUIPluginConfig
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.*

class KNestPluginConfiguration {

    fun cors(configuration: CORSConfig.() -> Unit) {
        CorsConfiguration.set(configuration)
    }

    fun logging(configuration: CallLoggingConfig.() -> Unit) {
        CallLoggingConfiguration.set(configuration)
    }

    fun contentNegotiation(configuration: ContentNegotiationConfig.() -> Unit) {
        ContentNegotiationConfiguration.set(configuration)
    }

    fun swagger(configuration: SwaggerUIPluginConfig.() -> Unit) {
        SwaggerConfiguration.set(configuration)
    }

    fun exceptionHandling(configuration: ExceptionConfiguration.() -> Unit) {
        ExceptionConfiguration.apply(configuration)
    }

    fun framework(configuration: FrameworkConfiguration.() -> Unit) {
        FrameworkConfiguration.apply(configuration)
    }
}