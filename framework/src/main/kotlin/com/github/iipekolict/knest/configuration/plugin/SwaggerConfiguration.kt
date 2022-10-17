package com.github.iipekolict.knest.configuration.plugin

import com.github.iipekolict.knest.configuration.PluginConfiguration
import io.github.smiley4.ktorswaggerui.SwaggerUIPluginConfig

object SwaggerConfiguration : PluginConfiguration<SwaggerUIPluginConfig.() -> Unit>() {

    override var config: SwaggerUIPluginConfig.() -> Unit = {}
}