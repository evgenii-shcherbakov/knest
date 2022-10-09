package com.github.iipekolict.preview.controller

import com.github.iipekolict.knest.annotations.classes.Controller
import com.github.iipekolict.knest.annotations.methods.Get
import com.github.iipekolict.knest.annotations.properties.Param

@Controller("pipe")
class PipeController {

    @Get("{id}/int")
    suspend fun getParamInt(@Param("id") id: Int): Map<String, Any> {
        return mapOf("id" to id)
    }

    @Get("{id}/float")
    suspend fun getParamFloat(@Param("id") id: Float): Map<String, Any> {
        return mapOf("id" to id)
    }

    @Get("{id}/double")
    suspend fun getParamDouble(@Param("id") id: Double): Map<String, Any> {
        return mapOf("id" to id)
    }
}