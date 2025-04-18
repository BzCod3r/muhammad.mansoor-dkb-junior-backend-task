package com.example.dkb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories("com.example.dkb.infrastructure.persistence.repository.jpa")
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
