package com.github.iipekolict.preview

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MiddlewareControllerTest {

    private val number = 42

    @Test
    fun testLocalMiddleware() = testApplication {
        val middlewareResponse: String = client.get("/middleware/check42/$number").bodyAsText()
        val defaultResponse: String = client.get("/middleware/check42/${number * 2}").bodyAsText()

        assertTrue("Response shouldn't be empty") {
            middlewareResponse.isNotBlank() && defaultResponse.isNotBlank()
        }

        assertEquals(
            expected = "Middleware works!",
            actual = middlewareResponse,
            message = "Middleware should execute"
        )

        assertEquals(
            expected = "Middleware not works!",
            actual = defaultResponse,
            message = "Middleware shouldn't execute"
        )
    }

    @Test
    fun testGlobalMiddleware() = testApplication {
        val middlewareResponse: String = client.get("/middleware/global").bodyAsText()

        assertTrue("Response shouldn't be empty") {
            middlewareResponse.isNotBlank()
        }

        assertEquals(
            expected = "123",
            actual = middlewareResponse,
            message = "Global middleware should execute"
        )
    }
}