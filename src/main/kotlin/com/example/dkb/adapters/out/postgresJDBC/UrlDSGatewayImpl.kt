package com.example.dkb.adapters.out.postgresJDBC

import com.example.dkb.adapters.out.postgresJDBC.entity.UrlEntity
import com.example.dkb.adapters.out.postgresJDBC.repository.UrlRepository
import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.gateway.UrlDSGateway
import com.example.dkb.infrastructure.config.HashidsConfig
import com.example.dkb.infrastructure.exception.DuplicateUrlException
import com.example.dkb.infrastructure.exception.InvalidUrlException
import com.example.dkb.infrastructure.exception.UrlNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UrlDSGatewayImpl(
    private val urlRepository: UrlRepository,
    private val hashidsConfig: HashidsConfig
): UrlDSGateway {
    override fun findAll(): List<UrlResponse> {
        return urlRepository.findAll()
            .filter { it.id != null }
            .map { urlEntity ->
                UrlResponse(
                    url = urlEntity.url,
                    shortUrl = hashidsConfig.encode(urlEntity.id!!)
                )
            }
    }


    override fun save(command: CreateUrlRequest): UrlResponse {
        val existingUrl= urlRepository.findUrlEntityByUrl(command.url)
        if (existingUrl != null) {
            throw DuplicateUrlException("The URL already exists.")
        }
        val entity = urlRepository.save(
            UrlEntity(url = command.url)
        )

        if (entity.id == null) throw InvalidUrlException("The ID of the URL is invalid.")

        val shortId = hashidsConfig.encode(entity.id)
        return UrlResponse(
            url = command.url,
            shortUrl = shortId,
        )
    }

    override fun findByShortCode(code: String): UrlResponse {
        println("findByShortCode $code")
        try {
            val decodeShortId = hashidsConfig.decode(code)
            val findEntity = urlRepository.findByIdOrNull(decodeShortId)
                ?: throw UrlNotFoundException("The URL does not exist.")
            return UrlResponse(
                url = findEntity.url,
                shortUrl = code
            )
        } catch (e: NoSuchElementException) {
            throw UrlNotFoundException("The URL is not found.")
        }

    }
}