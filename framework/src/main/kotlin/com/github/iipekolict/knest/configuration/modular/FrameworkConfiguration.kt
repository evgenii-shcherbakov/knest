package com.github.iipekolict.knest.configuration.modular

import com.github.iipekolict.knest.configuration.ModularConfiguration
import com.github.iipekolict.knest.injectors.PropertyInjector
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

    override fun get(): Configuration {
        return Configuration(controllers, propertyInjectors)
    }

    fun setControllers(vararg newControllers: Any) {
        controllers.addAll(newControllers)
    }

    fun addPropertyInjectors(vararg injectors: KClass<out PropertyInjector<out Annotation, out Any?>>) {
        propertyInjectors.addAll(injectors)
    }
}