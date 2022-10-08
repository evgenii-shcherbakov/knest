package by.iipekolict.knest

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.SwaggerUIPluginConfig
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.*
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.routing.*

class KNestConfiguration(
    val controllers: Set<Any>,
    val corsConfiguration: (CORSConfig.() -> Unit)?,
    val callLoggingConfiguration: (CallLoggingConfig.() -> Unit),
    val contentNegotiationConfiguration: (ContentNegotiationConfig.() -> Unit),
    val swaggerConfiguration: (SwaggerUIPluginConfig.() -> Unit)
)

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
}

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
        Reflector(this).injectControllers(configuration.controllers)
    }
}