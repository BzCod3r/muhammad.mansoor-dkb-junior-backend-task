package com.example.dkb.application.usecase

import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.application.gateway.UrlDSGateway
import org.springframework.stereotype.Service

@Service
class ShortenUrlUseCase(
    private val urlDSGateway: UrlDSGateway
)  {
    operator fun  invoke(command: CreateUrlRequest): UrlResponse {
        require(command.url.isNotBlank()) { "URL cannot be empty" }
        return urlDSGateway.save(command)
    }
}