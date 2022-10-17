package com.github.iipekolict.knest.injectors.properties

import com.github.iipekolict.knest.GlobalStorage
import com.github.iipekolict.knest.annotations.properties.AppConfig
import com.github.iipekolict.knest.injectors.PropertyInjector
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