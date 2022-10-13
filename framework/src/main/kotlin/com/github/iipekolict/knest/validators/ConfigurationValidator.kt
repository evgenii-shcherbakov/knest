package com.github.iipekolict.knest.validators

abstract class ConfigurationValidator<C> {

    abstract fun validate(configuration: C)
}