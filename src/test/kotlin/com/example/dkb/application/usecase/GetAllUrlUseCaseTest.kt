package com.example.dkb.application.usecase

import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.gateway.UrlDSGateway
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetAllUrlUseCaseTest {

    private val urlDSGateway = mockk<UrlDSGateway>()
    private val getAllUrlUseCase = GetAllUrlUseCase(urlDSGateway)

    @Test
    fun `should return active urls`() {
        // Arrange
        val rawData = listOf(
            UrlResponse("https://expired.com", "exp123"),
            UrlResponse("https://new.com", "new456"),
            UrlResponse("https://old.com", "old789")
        )

        every { urlDSGateway.findAll() } returns rawData

        // Act
        val actual = getAllUrlUseCase()

        // Assert
        assertEquals(rawData.size, actual.size) // Only active URLs
        assertEquals("https://new.com", actual[1].url) // Verify sorting
    }

    
}