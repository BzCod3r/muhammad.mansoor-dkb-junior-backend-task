package com.example.dkb.adapters.`in`.web

import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.usecase.GetAllUrlUseCase
import com.example.dkb.application.usecase.ResolveUrlUseCase
import com.example.dkb.application.usecase.ShortenUrlUseCase
import com.example.dkb.infrastructure.exception.UrlNotFoundException
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/urls")
class UrlShortenerController(
    private val shortenUrlUseCase: ShortenUrlUseCase,
    private val resolveUrlUseCase: ResolveUrlUseCase,
    private val getAllUrlUseCase: GetAllUrlUseCase
) {

    val logger= LoggerFactory.getLogger(this::class.java)


    @GetMapping
    fun getAllUrls()= ResponseEntity.ok().body(getAllUrlUseCase())

    @PostMapping
    fun createShortUrl(@Valid @RequestBody request: CreateUrlRequest): ResponseEntity<UrlResponse> {
        logger.info("Shorten request received for URL: ${request.url}")

        val response= shortenUrlUseCase(request)
            .let { shortened ->
                ResponseEntity.created(
                    URI.create("/api/v1/urls/${shortened.shortUrl}")
                ).body(shortened)
            }
        logger.info("Shorten request response: $response")

        return response
    }

    @GetMapping("/{code}")
    fun resolveShortCode(@PathVariable("code") code: String): ResponseEntity<UrlResponse> {
        logger.info("Resolve request received for code: {}", code)

        return try {
            val resolveUrl = resolveUrlUseCase(code)
            logger.debug("Successfully resolved code: {} to URL: {}", code, resolveUrl.url.take(50))

            ResponseEntity.ok().body(resolveUrl)
        } catch (ex: UrlNotFoundException) {
            logger.warn("Failed to resolve code: {} - Not Found", code)
            throw ex
        } catch (ex: Exception) {
            logger.error("Unexpected error resolving code: {}", code, ex)
            throw ex
        }
    }

}