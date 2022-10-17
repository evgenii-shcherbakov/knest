package com.github.iipekolict.knest.configuration.plugin

import com.github.iipekolict.knest.configuration.PluginConfiguration
import io.ktor.server.plugins.callloging.*

object CallLoggingConfiguration : PluginConfiguration<CallLoggingConfig.() -> Unit>() {

    override var config: CallLoggingConfig.() -> Unit = {}
}