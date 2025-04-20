package com.example.dkb.adapters

import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.usecase.GetAllUrlUseCase
import com.example.dkb.application.usecase.ResolveUrlUseCase
import com.example.dkb.application.usecase.ShortenUrlUseCase
import com.example.dkb.infrastructure.exception.InvalidUrlException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.net.URI

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
    fun `GET all URLs should return 200 OK`() {
        // Given
        val expectedResponse = listOf(
            UrlResponse("https://dkb-muhammad-task.com", "abc123"),
            UrlResponse("https://dkb-muhammad-task2.com", "abc1234"),
        )
        every { getAllUrlUseCase() } returns expectedResponse

        // When/Then
        mockMvc.perform(get("/api/v1/urls"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].shortUrl").value("abc123"))
            .andExpect(jsonPath("$[0].url").value("https://dkb-muhammad-task.com"))
            .andExpect(jsonPath("$[1].shortUrl").value("abc1234"))
            .andExpect(jsonPath("$[1].url").value("https://dkb-muhammad-task2.com"))

        verify { getAllUrlUseCase() }
    }

    @Test
    fun `POST create short URL should return 201 Created`() {
        // Given
        val requestJson = """{"url": "https://example.com"}"""
        val response = UrlResponse("https://example.com", "abc123")
        every { shortenUrlUseCase(any()) } returns response

        // When/Then
        mockMvc.perform(
            post("/api/v1/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        )
            .andExpect(status().isCreated)
            .andExpect(header().string("Location", "/api/v1/urls/abc123"))
            .andExpect(jsonPath("$.shortUrl").value("abc123"))
            .andExpect(jsonPath("$.url").value("https://example.com"))

        verify { shortenUrlUseCase(any()) }
    }

    @Test
    fun `GET resolve short code should return 200 OK`() {
        // Given
        val code = "abc123"
        val response = UrlResponse(shortUrl = code, url = "https://dkb-mansoor-task.com")
        every { resolveUrlUseCase(code) } returns response

        // When/Then
        mockMvc.perform(get("/api/v1/urls/$code"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.shortUrl").value(code))
            .andExpect(jsonPath("$.url").value("https://dkb-mansoor-task.com"))

        verify { resolveUrlUseCase(code) }
    }

    @Test
    fun `POST with invalid URL should return 400 Bad Request`() {
        // Given
        val invalidRequestJson = """{"url": "invalid-url"}"""
        val invalidRequest = CreateUrlRequest("invalid-url")

        every { shortenUrlUseCase(invalidRequest) } throws InvalidUrlException("Invalid URL")

        // When/Then
        mockMvc.perform(
            post("/api/v1/urls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequestJson)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("Invalid URL"))

        verify { shortenUrlUseCase(invalidRequest) }
    }

    @Test
    fun `GET non-existent short code should return 404 Not Found`() {
        // Given
        val nonExistentCode = "nonexist"
        every { resolveUrlUseCase(nonExistentCode) } throws NoSuchElementException("URL not found")

        // When/Then
        mockMvc.perform(get("/api/v1/urls/$nonExistentCode"))
            .andExpect(status().isNotFound)

        verify { resolveUrlUseCase(nonExistentCode) }
    }

}