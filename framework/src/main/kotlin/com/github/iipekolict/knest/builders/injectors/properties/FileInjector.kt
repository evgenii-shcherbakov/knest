package com.github.iipekolict.knest.builders.injectors.properties

import com.github.iipekolict.knest.annotations.properties.File
import io.ktor.http.content.*
import io.ktor.server.request.*
import kotlin.reflect.full.findAnnotation

class FileInjector : PropertyInjector<File, Any?>() {

    override fun findAnnotation(): File? {
        return parameter.findAnnotation()
    }

    override suspend fun inject(): Any? {
        return if (annotation.name.isNotBlank()) {
            var fileItem: PartData.FileItem? = null

            call.receiveMultipart().forEachPart {
                if (it is PartData.FileItem && it.name == annotation.name) {
                    fileItem = it
                }
            }

            fileItem
        } else {
            val fileItems = mutableSetOf<PartData.FileItem>()

            call.receiveMultipart().forEachPart {
                if (it is PartData.FileItem) {
                    fileItems.add(it)
                }
            }

            fileItems.toSet()
        }
    }
}