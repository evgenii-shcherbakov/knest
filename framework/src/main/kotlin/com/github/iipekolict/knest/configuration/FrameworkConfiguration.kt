package com.github.iipekolict.knest.configuration

import com.github.iipekolict.knest.validators.FrameworkConfigurationValidator

object FrameworkConfiguration : ModularConfiguration<FrameworkConfiguration.Configuration>() {

    private val controllers: MutableSet<Any> = mutableSetOf()

    class Configuration(
        val controllers: Set<Any>
    ) {

        init {
            FrameworkConfigurationValidator.validate(this)
        }
    }

    override val configuration: Configuration
        get() = Configuration(controllers)

    fun setControllers(vararg newControllers: Any) {
        controllers.addAll(newControllers)
    }
}