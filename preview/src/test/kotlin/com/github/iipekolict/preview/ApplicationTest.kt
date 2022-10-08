package com.github.iipekolict.preview

import com.github.iipekolict.launch
import io.ktor.http.*
import io.ktor.client.request.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            launch()
        }

        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }
}