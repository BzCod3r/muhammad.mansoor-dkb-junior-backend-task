package com.example.dkb.application.usecase

import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.gateway.UrlDSGateway
import com.example.dkb.infrastructure.exception.InvalidUrlException
import org.springframework.stereotype.Service

@Service
class ShortenUrlUseCase(
    private val urlDSGateway: UrlDSGateway
) {
    private final val URL_REGEX = Regex("^(https?|ftp)://([a-z0-9-]+\\.)+[a-z]{2,}(/[^\\s?#]*)?(\\?[^\\s#]*)?(#\\S*)?$")

    // Keep the comprehensive validation here
    operator fun invoke(command: CreateUrlRequest): UrlResponse {
        val url= command.url.normalizeUrl()
        validateUrl(url)
        return urlDSGateway.save(CreateUrlRequest(url))
    }

    private fun String.normalizeUrl(): String {
        return this.trim()
            .lowercase()
            .let { url ->
                // Add https:// if no protocol exists
                if (!url.matches(Regex("^[a-z]+://.*"))) {
                    "https://$url"
                } else {
                    url
                }
            }
            .replace(Regex("/+$"), "") // Remove trailing slashes
            .replace(Regex("(?<!:)/+"), "/") // Remove duplicate slashes
            .replace(Regex("^(http://|https://)+"), "https://") // Normalize protocol
    }

    private fun validateUrl(url: String) {
        when {
            url.isBlank() -> throw InvalidUrlException("URL cannot be blank")
            url.length > 2048 -> throw InvalidUrlException("URL too long")
            !URL_REGEX.matches(url) -> throw InvalidUrlException("Invalid URL format")
        }
    }
}