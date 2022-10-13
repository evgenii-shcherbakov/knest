package com.github.iipekolict.knest.configuration

import io.ktor.server.plugins.contentnegotiation.*

object ContentNegotiationConfiguration : PluginConfiguration<ContentNegotiationConfig.() -> Unit>() {

    override var config: ContentNegotiationConfig.() -> Unit = {}
}