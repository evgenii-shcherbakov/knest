package com.github.iipekolict.knest.configuration

import io.ktor.server.plugins.callloging.*

object CallLoggingConfiguration : PluginConfiguration<CallLoggingConfig.() -> Unit>() {

    override var config: CallLoggingConfig.() -> Unit = {}
}