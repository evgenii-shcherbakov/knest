package com.github.iipekolict.knest

import com.github.iipekolict.knest.builders.ControllerBuilder
import com.github.iipekolict.knest.data.KNestConfiguration
import com.github.iipekolict.knest.data.KNestConfigurationPublic
import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.routing.*

val KNest = createApplicationPlugin(
    name = "KNest",
    createConfiguration = ::KNestConfigurationPublic
) {
    val configuration: KNestConfiguration = pluginConfig.configuration

    on(MonitoringEvent(ApplicationStarted)) { application ->
        if (application.pluginOrNull(CORS) == null && configuration.corsConfiguration != null) {
            application.install(CORS, configuration.corsConfiguration)
        }

        if (application.pluginOrNull(CallLogging) == null) {
            application.install(CallLogging, configuration.callLoggingConfiguration)
        }

        if (application.pluginOrNull(ContentNegotiation) == null) {
            application.install(ContentNegotiation, configuration.contentNegotiationConfiguration)
        }

        if (application.pluginOrNull(SwaggerUI) == null) {
            application.install(SwaggerUI, configuration.swaggerConfiguration)
        }
    }

    application.routing {
        configuration.controllers.map {
            ControllerBuilder(this, it).build()
        }
    }
}