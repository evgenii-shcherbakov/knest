package com.github.iipekolict.knest.configuration.plugin

import com.github.iipekolict.knest.configuration.PluginConfiguration
import io.ktor.server.plugins.contentnegotiation.*

object ContentNegotiationConfiguration : PluginConfiguration<ContentNegotiationConfig.() -> Unit>() {

    override var config: ContentNegotiationConfig.() -> Unit = {}
}