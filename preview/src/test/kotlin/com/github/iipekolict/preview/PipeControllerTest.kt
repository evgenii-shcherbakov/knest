package com.github.iipekolict.preview

import com.google.gson.annotations.SerializedName
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*
import io.ktor.server.testing.*
import io.ktor.util.reflect.*
import org.junit.Test
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PipeControllerTest {

    data class Response(@SerializedName("id") val id: Any)

    private val number = 42

    private fun testPipeEndpoint(type: KClass<*>) {
        return testApplication {
            val httpClient = createClient {
                install(ContentNegotiation) {
                    gson()
                }
            }

            httpClient
                .get("/pipe/$number/${type.jvmName.lowercase()}")
                .apply {
                    val response: Response = body()

                    assertNotNull(response, "Response shouldn't be empty")

                    assertTrue("${type.jvmName} pipe was broken") {
                        response.id != number.toString()
                    }
                }
            }
    }

    @Test
    fun testIntPipe() = testPipeEndpoint(Int::class)

    @Test
    fun testFloatPipe() = testPipeEndpoint(Float::class)

    @Test
    fun testDoublePipe() = testPipeEndpoint(Double::class)
}