package com.example.dkb.application.dto

import java.io.Serializable

data class CreateUrlRequest(
    val url:String
)

data class UrlResponse(
    val url:String,
    val shortUrl:String
): Serializable

data class ReadUrlRequest(
    val shortUrl:String
)