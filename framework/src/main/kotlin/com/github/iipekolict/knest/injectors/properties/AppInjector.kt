package com.github.iipekolict.knest.injectors.properties

import com.github.iipekolict.knest.GlobalStorage
import com.github.iipekolict.knest.annotations.properties.App
import com.github.iipekolict.knest.injectors.PropertyInjector
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