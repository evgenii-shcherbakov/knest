package io.github.evgenii_shcherbakov.knest.injectors.properties

import io.github.evgenii_shcherbakov.knest.GlobalStorage
import io.github.evgenii_shcherbakov.knest.annotations.properties.AppConfig
import io.github.evgenii_shcherbakov.knest.injectors.PropertyInjector
import kotlin.reflect.full.findAnnotation

class AppConfigInjector : PropertyInjector<AppConfig, Any?>() {

    override fun findAnnotation(): AppConfig? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): Any? {
        return if (annotation.name.isNotBlank()) {
            if (annotation.isOptional) {
                GlobalStorage.getAppConfig().propertyOrNull(annotation.name)
            } else {
                GlobalStorage.getAppConfig().property(annotation.name)
            }
        } else {
            GlobalStorage.getAppConfig()
        }
    }
}