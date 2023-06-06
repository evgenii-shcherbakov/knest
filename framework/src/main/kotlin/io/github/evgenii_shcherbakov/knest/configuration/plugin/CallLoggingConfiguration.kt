package io.github.evgenii_shcherbakov.knest.configuration.plugin

import io.github.evgenii_shcherbakov.knest.configuration.PluginConfiguration
import io.ktor.server.plugins.callloging.*

object CallLoggingConfiguration : PluginConfiguration<CallLoggingConfig.() -> Unit>() {

    override var config: CallLoggingConfig.() -> Unit = {}
}