package com.example.dkb.adapters.`in`.web

import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.ReadUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.usecase.GetAllUrlUseCase
import com.example.dkb.application.usecase.ResolveUrlUseCase
import com.example.dkb.application.usecase.ShortenUrlUseCase
import org.springframework.http.HttpStatus
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

    @GetMapping
    fun getAllUrls()= ResponseEntity.ok().body(getAllUrlUseCase())

    @PostMapping
    fun createShortUrl(@RequestBody request: CreateUrlRequest): ResponseEntity<UrlResponse> {
        val shortenUrl = shortenUrlUseCase(request)
        return ResponseEntity.created(
            URI.create("/api/v1/urls/${shortenUrl.shortUrl}")
        ).body(
            shortenUrl
        )
    }

    @GetMapping("/{code}")
    fun resolveShortCode(@PathVariable("code") code: String): ResponseEntity<UrlResponse> {
        val resolveUrl= resolveUrlUseCase(code)
        return ResponseEntity.ok().body(
            resolveUrl
        )
    }

}