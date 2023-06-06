package io.github.evgenii_shcherbakov.knest.data

import kotlin.reflect.KFunction

data class HandlerData(
    val container: Any?,
    val handler: KFunction<*>
) {

    val fullName: String
        get() {
            if (container == null || container::class.simpleName == null) {
                return handler.name
            }

            return "${container::class.simpleName}.${handler.name}"
        }
}