package com.github.iipekolict.knest.configuration

abstract class PluginConfiguration<C> {

    protected abstract var config: C

    fun get(): C = config

    fun set(configuration: C) {
        config = configuration
    }
}