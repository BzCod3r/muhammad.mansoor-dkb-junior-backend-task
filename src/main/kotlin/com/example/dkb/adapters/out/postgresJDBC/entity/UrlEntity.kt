package com.example.dkb.adapters.out.postgresJDBC.entity

import jakarta.persistence.*

@Entity
@Table(name = "urls")
data class UrlEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, columnDefinition = "TEXT", unique = true)
    val url: String  // Remove JPA validations
) {
    constructor() : this(null, "")

}