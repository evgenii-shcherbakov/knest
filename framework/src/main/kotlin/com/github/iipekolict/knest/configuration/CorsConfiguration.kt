package com.github.iipekolict.knest.configuration

import io.ktor.server.plugins.cors.*

object CorsConfiguration : PluginConfiguration<CORSConfig.() -> Unit>() {

    override var config: CORSConfig.() -> Unit = {}
}