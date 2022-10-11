package com.github.iipekolict.knest.configuration

import com.github.iipekolict.knest.validators.FrameworkConfigurationValidator

object FrameworkConfiguration {

    private val controllers: MutableSet<Any> = mutableSetOf()

    class Configuration(
        val controllers: Set<Any>
    ) {

        init {
            FrameworkConfigurationValidator.validate(this)
        }
    }

    val configuration: Configuration
        get() = Configuration(controllers)

    fun setControllers(vararg newControllers: Any) {
        controllers.addAll(newControllers)
    }
}