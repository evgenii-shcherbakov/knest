package com.github.iipekolict.knest

import com.github.iipekolict.knest.exceptions.KNestException
import io.ktor.server.application.*
import io.ktor.server.config.*

object GlobalStorage {

    private var app: Application? = null
    private var appConfig: ApplicationConfig? = null

    fun getApp(): Application {
        return app ?: throw KNestException("App not exists")
    }

    fun setApp(application: Application) {
        app = application
    }

    fun getAppConfig(): ApplicationConfig {
        return appConfig ?: throw KNestException("App config not exists")
    }

    fun setAppConfig(config: ApplicationConfig) {
        appConfig = config
    }
}