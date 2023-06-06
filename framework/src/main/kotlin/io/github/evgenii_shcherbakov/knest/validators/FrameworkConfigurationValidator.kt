package io.github.evgenii_shcherbakov.knest.validators

import io.github.evgenii_shcherbakov.knest.annotations.classes.Controller
import io.github.evgenii_shcherbakov.knest.configuration.modular.FrameworkConfiguration
import io.github.evgenii_shcherbakov.knest.exceptions.KNestException
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