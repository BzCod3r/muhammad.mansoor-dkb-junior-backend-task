package com.example.dkb.domain.gateway

import com.example.dkb.application.dto.CreateUrlRequest
import com.example.dkb.application.dto.ReadUrlRequest
import com.example.dkb.application.dto.UrlResponse


interface UrlDSGateway {
    fun save(command: CreateUrlRequest): UrlResponse
    fun findByShortCode(command: ReadUrlRequest): UrlResponse?
}