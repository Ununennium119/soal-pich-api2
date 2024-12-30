package com.example.soalpichapi2.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginRequest(
    @field:NotBlank(message = "username.empty")
    @field:Size(min = 3, max = 48, message = "username.size")
    val username: String,

    @field:NotBlank(message = "password.empty")
    @field:Size(min = 3, max = 48, message = "password.size")
    val password: String,
)
