package com.example.dkb.application.usecase

import com.example.dkb.application.gateway.UrlDSGateway
import org.springframework.stereotype.Service

@Service
class GetAllUrlUseCase(
    private val urlDSGateway: UrlDSGateway
) {
    operator fun invoke()= urlDSGateway.findAll()
}