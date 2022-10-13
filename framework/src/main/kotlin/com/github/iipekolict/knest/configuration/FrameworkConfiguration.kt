package com.github.iipekolict.knest.configuration

import com.github.iipekolict.knest.builders.injectors.properties.PropertyInjector
import com.github.iipekolict.knest.validators.FrameworkConfigurationValidator
import kotlin.reflect.KClass

object FrameworkConfiguration : ModularConfiguration<FrameworkConfiguration.Configuration>() {

    private val controllers: MutableSet<Any> = mutableSetOf()
    private val propertyInjectors = mutableSetOf<KClass<out PropertyInjector<out Annotation, out Any?>>>()

    class Configuration(
        val controllers: Set<Any>,
        val propertyInjectors: Set<KClass<out PropertyInjector<out Annotation, out Any?>>>
    ) {

        init {
            FrameworkConfigurationValidator.validate(this)
        }
    }

    override val configuration: Configuration
        get() = Configuration(controllers, propertyInjectors)

    fun setControllers(vararg newControllers: Any) {
        controllers.addAll(newControllers)
    }

    fun addPropertyInjectors(vararg injectors: KClass<out PropertyInjector<out Annotation, out Any?>>) {
        propertyInjectors.addAll(injectors)
    }
}