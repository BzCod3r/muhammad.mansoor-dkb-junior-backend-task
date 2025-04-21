package com.example.dkb.application.usecase

import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.gateway.UrlDSGateway
import com.example.dkb.infrastructure.exception.InvalidUrlException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class ShortenUrlUseCaseTest {
    private val mockGateway = mockk<UrlDSGateway>()
    private val useCase = ShortenUrlUseCase(mockGateway)

    @Test
    fun `should save URL through gateway`() {
        // Given
        val request = CreateUrlRequest("https://dkb-task.com")
        val expectedResponse = UrlResponse("https://dkb-task.com", "abc123")
        every { mockGateway.save(request) } returns expectedResponse

        // When
        val result = useCase(request)

        // Then
        assertEquals(expectedResponse, result)
        verify { mockGateway.save(request) }
    }

    @Test
    fun `should throw for blank URL`() {
        // When/Then
        assertThrows<InvalidUrlException> {
            useCase(CreateUrlRequest(""))
        }
    }

    @Test
    fun `should throw for invalid URL format`() {
        // When/Then
        assertThrows<InvalidUrlException> {
            useCase(CreateUrlRequest("invalid-url"))
        }
    }

    @Test
    fun `should normalize URLs before saving`() {
        // Given
        val testCases = mapOf(
            "HTTPS://EXAMPLE.COM" to "https://example.com",
            "http://example.com" to "https://example.com",
            "example.com" to "https://example.com"
        )

        testCases.forEach { (input, expected) ->
            every { mockGateway.save(CreateUrlRequest(expected)) } returns
                    UrlResponse(expected, "short123")

            // When
            val result = useCase(CreateUrlRequest(input))

            // Then
            assertEquals(expected, result.url)
            verify { mockGateway.save(CreateUrlRequest(expected)) }
        }
    }
}