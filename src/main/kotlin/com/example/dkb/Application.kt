package com.example.dkb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories("com.example.dkb.adapters.out.postgresJDBC.repository")
@EnableCaching
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
