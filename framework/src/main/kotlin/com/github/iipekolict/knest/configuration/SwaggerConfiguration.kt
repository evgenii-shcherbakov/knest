package com.github.iipekolict.knest.configuration

import io.github.smiley4.ktorswaggerui.SwaggerUIPluginConfig

object SwaggerConfiguration : PluginConfiguration<SwaggerUIPluginConfig.() -> Unit>() {

    override var config: SwaggerUIPluginConfig.() -> Unit = {}
}