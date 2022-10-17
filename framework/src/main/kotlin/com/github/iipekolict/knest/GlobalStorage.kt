package com.github.iipekolict.knest

import com.github.iipekolict.knest.exceptions.KNestException
import io.ktor.server.application.*

object GlobalStorage {

    private var app: Application? = null

    fun getApp(): Application {
        return app ?: throw KNestException("App not exists")
    }

    fun setApp(application: Application) {
        app = application
    }
}