package com.example.dkb

import com.example.dkb.infrastructure.exception.InvalidUrlException
import com.example.dkb.adapters.out.postgresJDBC.entity.UrlEntity
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class UrlEntityTest {
    @Test
    fun `should create entity for valid URL`() {
        val validUrl= "https://example.com/path"
        val entity = UrlEntity.create(validUrl)
        assertEquals(validUrl,entity.url)
    }

    @Test
    fun `should properly normalize URLs`() {
        val testCases = mapOf(
            "HTTPS://EXAMPLE.COM/// " to "https://example.com",
            "http://example.com/path///" to "http://example.com/path",
            "example.com" to "http://example.com", // Auto-add protocol
            "http://example.com//path//to//resource" to "http://example.com/path/to/resource",
            "  http://example.com  " to "http://example.com"
        )

        testCases.forEach { (input, expected) ->
            assertEquals(expected, UrlEntity.create(input).url,
                "Failed for input: '$input'")
        }
    }

    @Test
    fun `should reject blank URL`() {
        assertThrows<InvalidUrlException> { UrlEntity.create("")  }
    }

    @Test
    fun `should rejec invalid format`() {
        assertThrows<InvalidUrlException> { UrlEntity.create("example.com")  }
    }


}