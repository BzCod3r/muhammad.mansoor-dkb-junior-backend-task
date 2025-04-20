package com.example.dkb.application

import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.usecase.ResolveUrlUseCase
import com.example.dkb.application.gateway.UrlDSGateway
import com.example.dkb.infrastructure.exception.UrlNotFoundException
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class ResolveUrlUseCaseTest {
    private val urlDSGateway = mockk<UrlDSGateway>()
    private val resolveUrlUseCase = ResolveUrlUseCase(urlDSGateway)

    @Test
    fun `should return UrlResponse when short code exists`() {
        // Arrange
        val shortCode = "abc123"
        val expectedResponse = UrlResponse("https://example.com", shortCode)

        every { urlDSGateway.findByShortCode(shortCode) } returns expectedResponse

        // Act
        val actual = resolveUrlUseCase(shortCode)

        // Assert
        assertEquals(expectedResponse, actual)
    }

    @Test
    fun `should throw UrlNotFoundException when short code does not exist`() {
        // Arrange
        val shortCode = "notfound123"
        every { urlDSGateway.findByShortCode(shortCode) } returns null

        // Act & Assert
        val exception = assertThrows<UrlNotFoundException> {
            resolveUrlUseCase(shortCode)
        }

        assertEquals("$shortCode not found", exception.message)
    }
}