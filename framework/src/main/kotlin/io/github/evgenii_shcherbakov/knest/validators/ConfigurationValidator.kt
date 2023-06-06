package io.github.evgenii_shcherbakov.knest.validators

abstract class ConfigurationValidator<C> {

    abstract fun validate(configuration: C)
}