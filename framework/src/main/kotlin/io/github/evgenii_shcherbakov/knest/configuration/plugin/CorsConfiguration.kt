package io.github.evgenii_shcherbakov.knest.configuration.plugin

import io.github.evgenii_shcherbakov.knest.configuration.PluginConfiguration
import io.ktor.server.plugins.cors.*

object CorsConfiguration : PluginConfiguration<CORSConfig.() -> Unit>() {

    override var config: CORSConfig.() -> Unit = {}
}