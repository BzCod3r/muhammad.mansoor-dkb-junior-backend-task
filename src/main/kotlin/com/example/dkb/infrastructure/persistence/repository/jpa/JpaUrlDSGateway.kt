package com.example.dkb.infrastructure.persistence.repository.jpa

import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.ReadUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.domain.exception.DuplicateUrlException
import com.example.dkb.domain.exception.InvalidUrlException
import com.example.dkb.domain.exception.UrlNotFoundException
import com.example.dkb.domain.gateway.UrlDSGateway
import com.example.dkb.infrastructure.config.HashidsConfig
import com.example.dkb.infrastructure.persistence.entity.UrlEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class JpaUrlDSGateway(
    private val springUrlDataRepository: SpringUrlDataRepository,
    private val hashidsConfig: HashidsConfig
) :
    UrlDSGateway {
    override fun save(command: CreateUrlRequest): UrlResponse {
        val existingUrl= springUrlDataRepository.findUrlEntityByUrl(command.url)
        if (existingUrl != null) {
            throw DuplicateUrlException("The URL already exists.")
        }
        val entity = springUrlDataRepository.save(
            UrlEntity.create(
                url = command.url
            )
        )

        if (entity.id == null) throw InvalidUrlException("The ID of the URL is invalid.")

        val shortId = hashidsConfig.encode(entity.id)
        return UrlResponse(
            url = command.url,
            shortUrl = shortId,
        )
    }

    override fun findByShortCode(command: ReadUrlRequest): UrlResponse {
        val decodeShortId = hashidsConfig.decode(command.shortUrl)
        val findEntity = springUrlDataRepository.findByIdOrNull(decodeShortId)
            ?: throw UrlNotFoundException("The URL does not exist.")
        return UrlResponse(
            url = findEntity.url,
            shortUrl = command.shortUrl
        )
    }
}