package com.example.dkb.config

import com.example.dkb.application.usecase.GetAllUrlUseCase
import com.example.dkb.application.usecase.ResolveUrlUseCase
import com.example.dkb.application.usecase.ShortenUrlUseCase
import io.mockk.mockk
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TestConfig {
    @Bean
    fun shortenUrlUseCase(): ShortenUrlUseCase = mockk()

    @Bean
    fun resolveUrlUseCase(): ResolveUrlUseCase = mockk()

    @Bean
    fun getAllUrlUseCase(): GetAllUrlUseCase = mockk()
}