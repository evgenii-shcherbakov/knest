package com.github.iipekolict.knest

import com.github.iipekolict.knest.builders.ControllerBuilder
import com.github.iipekolict.knest.configuration.*
import com.github.iipekolict.knest.configuration.modular.FrameworkConfiguration
import com.github.iipekolict.knest.configuration.plugin.*
import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.routing.*

val KNest = createApplicationPlugin(
    name = "KNest",
    createConfiguration = ::KNestPluginConfiguration
) {

    on(MonitoringEvent(ApplicationStarted)) { application ->
        if (application.pluginOrNull(CORS) == null) {
            application.install(CORS, CorsConfiguration.get())
        }

        if (application.pluginOrNull(CallLogging) == null) {
            application.install(CallLogging, CallLoggingConfiguration.get())
        }

        if (application.pluginOrNull(ContentNegotiation) == null) {
            application.install(ContentNegotiation, ContentNegotiationConfiguration.get())
        }

        if (application.pluginOrNull(SwaggerUI) == null) {
            application.install(SwaggerUI, SwaggerConfiguration.get())
        }

        if (application.pluginOrNull(Authentication) == null) {
            application.install(Authentication, AuthenticationConfiguration.get())
        }
    }

    GlobalStorage.setApp(application)

    application.routing {
        FrameworkConfiguration.configuration.controllers.map {
            ControllerBuilder(this, it).build()
        }
    }
}