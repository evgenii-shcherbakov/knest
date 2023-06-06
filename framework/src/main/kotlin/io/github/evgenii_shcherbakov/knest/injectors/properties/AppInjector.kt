package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.GlobalStorage
import io.github.evgenii_shcherbakov.knest.annotations.properties.App
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
import io.ktor.server.application.*
import kotlin.reflect.full.findAnnotation

class AppInjector : PropertyInjector<App, Application>() {

    override fun findAnnotation(): App? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): Application {
        return GlobalStorage.getApp()
    }
}