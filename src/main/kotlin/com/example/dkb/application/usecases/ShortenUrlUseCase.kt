package com.example.dkb.application.usecases

import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.UrlResponse
import com.example.dkb.domain.gateway.UrlDSGateway
import org.springframework.stereotype.Service

@Service
class ShortenUrlUseCase(
    private val urlDSGateway: UrlDSGateway
)  {
    operator fun  invoke(command: CreateUrlRequest): UrlResponse {
       return urlDSGateway.save(command)
    }
}