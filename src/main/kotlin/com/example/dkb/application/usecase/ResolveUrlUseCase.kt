package com.example.dkb.application.usecase

import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.gateway.UrlDSGateway
import com.example.dkb.infrastructure.exception.UrlNotFoundException
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service

@Service
class ResolveUrlUseCase(
    private val urlDSGateway: UrlDSGateway
) {
    operator fun invoke(code:String): UrlResponse?{
        val response= urlDSGateway.findByShortCode(code) ?: throw UrlNotFoundException("$code not found")
        return response
    }
}