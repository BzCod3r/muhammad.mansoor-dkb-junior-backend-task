package com.example.dkb.adapters.`in`.web

import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.usecase.GetAllUrlUseCase
import com.example.dkb.application.usecase.ResolveUrlUseCase
import com.example.dkb.application.usecase.ShortenUrlUseCase
import com.example.dkb.infrastructure.exception.InvalidUrlException
import com.example.dkb.infrastructure.exception.UrlNotFoundException
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class UrlControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var shortenUrlUseCase: ShortenUrlUseCase

    @Autowired
    private lateinit var resolveUrlUseCase: ResolveUrlUseCase

    @Autowired
    private lateinit var getAllUrlUseCase: GetAllUrlUseCase

    @Test
    fun `GET all URLs should return 200 with URL list`() {
        // Given
        val expectedResponse = listOf(
            UrlResponse("https://dkb-muhammad-task.com", "abc123"),
            UrlResponse("https://dkb-muhammad-task2.com", "abc1234")
        )
        every { getAllUrlUseCase() } returns expectedResponse

        // When/Then
        mockMvc.perform(get("/api/v1/urls"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].url").value("https://dkb-muhammad-task.com"))
            .andExpect(jsonPath("$[0].shortUrl").value("abc123"))
            .andExpect(jsonPath("$[1].url").value("https://dkb-muhammad-task2.com"))
            .andExpect(jsonPath("$[1].shortUrl").value("abc1234"))

        verify { getAllUrlUseCase() }
    }

    @Test
    fun `POST valid URL should return 201 with location header`() {
        // Given
        val response = UrlResponse("https://example.com", "abc123")
        every { shortenUrlUseCase(any()) } returns response

        // When/Then
        mockMvc.perform(
            post("/api/v1/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"url":"https://example.com"}""")
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("Location", "/api/v1/urls/abc123"))
            .andExpect(jsonPath("$.url").value("https://example.com"))
            .andExpect(jsonPath("$.shortUrl").value("abc123"))
    }

    @Test
    fun `POST invalid URL should return 400 with error`() {
        // Given
        every { shortenUrlUseCase(any()) } throws
                InvalidUrlException("Invalid URL")

        // When/Then
        mockMvc.perform(
            post("/api/v1/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"url":"invalid"}""")
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("Invalid URL"))
    }

    @Test
    fun `GET existing short URL should return URL details`() {
        // Given
        val response = UrlResponse("https://example.com", "abc123")
        every { resolveUrlUseCase("abc123") } returns response

        // When/Then
        mockMvc.perform(get("/api/v1/urls/abc123"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.url").value("https://example.com"))
            .andExpect(jsonPath("$.shortUrl").value("abc123"))
    }

    @Test
    fun `GET non-existent short URL should return 404`() {
        // Given
        every { resolveUrlUseCase("nonexist") } throws
                UrlNotFoundException("Not found")

        // When/Then
        mockMvc.perform(get("/api/v1/urls/nonexist"))
            .andExpect(status().isNotFound)
    }

}