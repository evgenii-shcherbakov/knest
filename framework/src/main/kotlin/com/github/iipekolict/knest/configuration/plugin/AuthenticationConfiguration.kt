package com.github.iipekolict.knest.configuration.plugin

import com.github.iipekolict.knest.configuration.PluginConfiguration
import io.ktor.server.auth.*

object AuthenticationConfiguration : PluginConfiguration<AuthenticationConfig.() -> Unit>() {

    override var config: AuthenticationConfig.() -> Unit = {}
}