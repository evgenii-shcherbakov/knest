package com.github.iipekolict.knest.validators

abstract class BaseValidator<C> {

    abstract fun validate(configuration: C)
}