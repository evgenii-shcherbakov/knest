package io.github.evgenii_shcherbakov.knest.configuration.plugin

import io.github.evgenii_shcherbakov.knest.configuration.PluginConfiguration
import io.ktor.server.plugins.contentnegotiation.*

object ContentNegotiationConfiguration : PluginConfiguration<ContentNegotiationConfig.() -> Unit>() {

    override var config: ContentNegotiationConfig.() -> Unit = {}
}