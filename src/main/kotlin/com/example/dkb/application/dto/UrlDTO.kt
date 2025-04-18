package com.example.dkb.application.dto

data class CreateUrlRequest(
    val url:String
)

data class UrlResponse(
    val url:String,
    val shortUrl:String
)

data class ReadUrlRequest(
    val shortUrl:String
)