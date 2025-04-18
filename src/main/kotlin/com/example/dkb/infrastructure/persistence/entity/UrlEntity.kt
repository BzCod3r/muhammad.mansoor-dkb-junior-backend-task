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
        regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*\$",
        message = "Invalid URL format"
    )
    val url: String,
) {
    // No-arg constructor for Hibernate
    constructor() : this(null, "")

    companion object {
        private val URL_REGEX = Regex("^(https?|ftp)://[^\\s/$.?#].[^\\s]*\$")

        fun create(url: String): UrlEntity {
            validateUrl(url)
            return UrlEntity(url = url.normalizeUrl())
        }

        private fun validateUrl(url: String) {
            when {
                url.isBlank() -> throw InvalidUrlException("URL cannot be blank")
                url.length > 2048 -> throw InvalidUrlException("URL too long")
                !URL_REGEX.matches(url) -> throw InvalidUrlException("Invalid URL format")
            }
        }

        private fun String.normalizeUrl(): String {
            return this.trim()
                .removeSuffix("/")
                .lowercase()
        }
    }
}