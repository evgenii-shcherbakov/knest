package com.github.iipekolict.knest.validators

import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.configuration.modular.FrameworkConfiguration
import com.github.iipekolict.knest.exceptions.KNestException
import kotlin.reflect.full.findAnnotation

object FrameworkConfigurationValidator : ConfigurationValidator<FrameworkConfiguration.Configuration>() {

    private fun validateControllers(controllers: Set<Any>) {
        if (controllers.any { it::class.findAnnotation<Controller>() == null }) {
            throw KNestException("Controller should be annotated by 'Controller' annotation")
        }
    }

    override fun validate(configuration: FrameworkConfiguration.Configuration) {
        validateControllers(configuration.controllers)
    }
}