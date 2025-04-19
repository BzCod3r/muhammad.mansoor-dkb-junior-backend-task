package com.example.dkb.infrastructure.persistence.entity

import com.example.dkb.domain.exception.InvalidUrlException
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

@Entity
@Table(name = "urls")
data class UrlEntity private constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, columnDefinition = "TEXT", unique = true)
    @field:NotBlank(message = "URL cannot be blank")
    @field:Size(max = 2048, message = "URL must be less than 2048 characters")
    @field:Pattern(
        regexp = "^" +
                "(https?|ftp)://" +  // Protocol
                "([a-zA-Z0-9.-]+)" +  // Domain
                "(\\.[a-zA-Z]{2,})" + // TLD
                "(/[^\\s?#]*)?" +     // Path
                "(\\?[^\\s#]*)?" +    // Query
                "(#\\S*)?" +          // Fragment
                "$",
        message = "Invalid URL format"
    )
    val url: String,
) {
    // No-arg constructor for Hibernate
    constructor() : this(null, "")

    companion object {
        private val URL_REGEX = Regex("^(https?|ftp)://([a-z0-9-]+\\.)+[a-z]{2,}(/[^\\s?#]*)?(\\?[^\\s#]*)?(#\\S*)?$")

        fun create(url: String): UrlEntity {
            val normalized = url.normalizeUrl() // Normalize FIRST
            validateUrl(normalized) // Then validate
            return UrlEntity(url = normalized)
        }

        private fun String.normalizeUrl(): String {
            return this.trim()
                .lowercase()
                .replace(Regex("/+$"), "") // Remove trailing slashes
                .replace(Regex("(?<!:)/+"), "/") // Remove duplicate slashes
                .replace(Regex("^http://http://"), "http://") // Fix double protocols
        }

        private fun validateUrl(url: String) {
            when {
                url.isBlank() -> throw InvalidUrlException("URL cannot be blank")
                url.length > 2048 -> throw InvalidUrlException("URL too long")
                !URL_REGEX.matches(url) -> throw InvalidUrlException("Invalid URL format")
            }
        }


    }
}