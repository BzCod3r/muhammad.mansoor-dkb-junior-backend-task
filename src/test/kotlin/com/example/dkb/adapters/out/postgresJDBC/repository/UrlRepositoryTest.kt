package com.example.dkb.adapters.out.postgresJDBC.repository

import com.example.dkb.adapters.out.postgresJDBC.entity.UrlEntity
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.Test

@DataJpaTest
class UrlRepositoryTest()  {
    @Autowired
    lateinit var repository: UrlRepository

    @Test
    fun `should save and retrieve URL`() {
        val entity = UrlEntity(url = "https://example.com")
        val saved = repository.save(entity)
        assertNotNull(saved.id)
    }
}