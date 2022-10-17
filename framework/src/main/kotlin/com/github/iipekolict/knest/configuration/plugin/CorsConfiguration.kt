package com.github.iipekolict.knest.configuration.plugin

import com.github.iipekolict.knest.configuration.PluginConfiguration
import io.ktor.server.plugins.cors.*

object CorsConfiguration : PluginConfiguration<CORSConfig.() -> Unit>() {

    override var config: CORSConfig.() -> Unit = {}
}