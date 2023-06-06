package io.github.evgenii_shcherbakov.knest.configuration.plugin

import io.github.evgenii_shcherbakov.knest.configuration.PluginConfiguration
import io.ktor.server.auth.*

object AuthenticationConfiguration : PluginConfiguration<AuthenticationConfig.() -> Unit>() {

    override var config: AuthenticationConfig.() -> Unit = {}
}