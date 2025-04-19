package com.example.dkb.application.gateway

import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.UrlResponse


interface UrlDSGateway {
    fun findAll(): List<UrlResponse>
    fun save(command: CreateUrlRequest): UrlResponse
    fun findByShortCode(code: String): UrlResponse?
}