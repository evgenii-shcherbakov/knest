package com.github.iipekolict.knest.configuration

abstract class ModularConfiguration<C> {

    abstract fun get(): C
}