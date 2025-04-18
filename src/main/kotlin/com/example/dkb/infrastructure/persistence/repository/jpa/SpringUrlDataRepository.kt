package com.example.dkb.infrastructure.persistence.repository.jpa

import com.example.dkb.infrastructure.persistence.entity.UrlEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SpringUrlDataRepository: JpaRepository<UrlEntity, Long> {
    fun findUrlEntityByUrl(url:String): UrlEntity?
}