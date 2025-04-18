package com.example.dkb.application.usecases

import com.example.dkb.application.dto.ReadUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.domain.gateway.UrlDSGateway
import org.springframework.stereotype.Service

@Service
class ResolveUrlUseCase(
    private val urlDSGateway: UrlDSGateway
) {
    operator fun invoke(request:ReadUrlRequest): UrlResponse?{
        return urlDSGateway.findByShortCode(request)
    }
}