package com.example.dkb.adapters.`in`.web.exception

import com.example.dkb.infrastructure.exception.InvalidShortCodeException
import com.example.dkb.infrastructure.exception.InvalidUrlException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(InvalidUrlException::class)
    fun handleInvalidUrl(ex: InvalidUrlException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(ex.message ?: "Invalid URL"))
    }

    @ExceptionHandler(InvalidShortCodeException::class)
    fun handleInvalidShortCode(ex: InvalidShortCodeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(ex.message ?: "Invalid ShortCode"))
    }


}

data class ErrorResponse(val message: String)