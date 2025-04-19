package com.example.dkb

import com.example.dkb.application.dto.ReadUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.usecases.ResolveUrlUseCase
import com.example.dkb.domain.gateway.UrlDSGateway
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ResolveUrlUseCaseTest {
    private val mockKGateway= mockk<UrlDSGateway>()
    private val useCase= ResolveUrlUseCase(mockKGateway)
    @Test
    fun `should return URL when found`() {
        val request= ReadUrlRequest("abc123")
        val expect= UrlResponse("https://dkb-task.com","abc123")
        every { mockKGateway.findByShortCode(request) } returns expect

        val result= useCase(request)
        assertEquals(expect,result)
    }
}