package io.github.evgenii_shcherbakov.knest.configuration

abstract class ModularConfiguration<C> {

    abstract fun get(): C
}