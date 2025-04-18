package com.example.dkb.infrastructure.api

import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.ReadUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.usecases.ResolveUrlUseCase
import com.example.dkb.application.usecases.ShortenUrlUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/urls")
class UrlShortenerController(
    private val shortenUrlUseCase: ShortenUrlUseCase,
    private val resolveUrlUseCase: ResolveUrlUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createShortUrl(@RequestBody request: CreateUrlRequest): ResponseEntity<UrlResponse> {
        val shortenUrl = shortenUrlUseCase(request)
        return ResponseEntity.created(
            URI.create("/api/v1/urls/${shortenUrl.shortUrl}")
        ).body(
            shortenUrl
        )
    }

    @PostMapping("/resolve")
    fun resolveShortCode(@RequestBody request: ReadUrlRequest): ResponseEntity<UrlResponse> {
        val resolveUrl= resolveUrlUseCase(request)
        return ResponseEntity.ok().body(
            resolveUrl
        )
    }

}