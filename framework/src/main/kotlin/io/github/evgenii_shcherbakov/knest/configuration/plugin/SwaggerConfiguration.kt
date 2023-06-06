package io.github.evgenii_shcherbakov.knest.configuration.plugin

import io.github.evgenii_shcherbakov.knest.configuration.PluginConfiguration
import io.github.smiley4.ktorswaggerui.SwaggerUIPluginConfig

object SwaggerConfiguration : PluginConfiguration<SwaggerUIPluginConfig.() -> Unit>() {

    override var config: SwaggerUIPluginConfig.() -> Unit = {}
}