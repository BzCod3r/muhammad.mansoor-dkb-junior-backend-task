package com.example.dkb.application.usecase

import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.gateway.UrlDSGateway
import com.example.dkb.infrastructure.exception.InvalidShortCodeException
import com.example.dkb.infrastructure.exception.UrlNotFoundException
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service

@Service
class ResolveUrlUseCase(
    private val urlDSGateway: UrlDSGateway
) {
    @Cacheable(value = ["resolvedUrl"], key = "#code")
    operator fun invoke(code: String): UrlResponse {
        val validatedCode = validateCode(code)
        return urlDSGateway.findByShortCode(validatedCode)
            ?: throw UrlNotFoundException("$validatedCode not found")
    }

    private fun validateCode(code: String): String {
        if (code.isBlank()) {
            throw InvalidShortCodeException("Short code cannot be blank")
        }

        val trimmedCode = code.trim()

        if (trimmedCode.length < MIN_SHORT_CODE_LENGTH) {
            throw InvalidShortCodeException("Short code must be at least $MIN_SHORT_CODE_LENGTH characters")
        }

        // Optional: Ensure only alphanumeric characters
        if (!trimmedCode.matches(Regex("^[a-zA-Z0-9]+$"))) {
            throw InvalidShortCodeException("Short code can only contain letters and numbers")
        }

        return trimmedCode
    }

    companion object {
        private const val MIN_SHORT_CODE_LENGTH = 8  // Adjust as needed
    }
}