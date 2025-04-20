package com.example.dkb.application

import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.usecase.ShortenUrlUseCase
import com.example.dkb.application.gateway.UrlDSGateway
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class ShortenUrlUseCaseTest {
    private val mockGateway= mockk<UrlDSGateway>()
    private val useCase= ShortenUrlUseCase(mockGateway)

    @Test
    fun `should save URL throught gateway`() {
        val request= CreateUrlRequest("https://dkb-task.com")
        val expectResonse= UrlResponse("https://dkb-task.com", "abc123")
        every { mockGateway.save(request) } returns expectResonse

        val result= useCase(request)

        assertEquals(expectResonse,result)
        verify { mockGateway.save(request) }
    }

    @Test
    fun `should fail for blank URL`() {
        assertThrows<IllegalArgumentException> { useCase(CreateUrlRequest("")) }
    }
}