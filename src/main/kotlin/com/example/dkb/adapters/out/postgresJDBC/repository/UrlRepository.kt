package com.example.dkb.adapters.out.postgresJDBC.repository

import com.example.dkb.adapters.out.postgresJDBC.entity.UrlEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository: JpaRepository<UrlEntity, Long> {
    fun findUrlEntityByUrl(url:String): UrlEntity?
}