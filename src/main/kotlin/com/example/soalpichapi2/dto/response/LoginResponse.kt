package com.example.soalpichapi2.dto.response

data class LoginResponse(
    val token: String,
    val expiration: Long,
)
