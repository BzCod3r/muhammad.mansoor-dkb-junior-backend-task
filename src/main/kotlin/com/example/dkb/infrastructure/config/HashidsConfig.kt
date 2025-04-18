package com.example.dkb.infrastructure.config

import org.hashids.Hashids
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class HashidsConfig(
    @Value("\${hashids.salt}") final val hashidsSalt: String,
) {

    private val hashids= Hashids(hashidsSalt,8)

    fun encode(value:Long):String {
        return hashids.encode(value)
    }

    fun decode(shortId:String):Long {
        return hashids.decode(shortId).first()
    }

}