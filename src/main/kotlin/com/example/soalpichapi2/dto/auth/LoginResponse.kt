package com.example.soalpichapi2.dto.auth

data class LoginResponse(
    val token: String,
    val expiration: Long,
)
