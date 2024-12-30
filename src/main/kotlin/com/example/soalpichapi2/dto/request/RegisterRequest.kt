package com.example.soalpichapi2.dto.request

import com.example.soalpichapi2.enumeration.UserRole
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class RegisterRequest(
    @field:NotBlank(message = "username.empty")
    @field:Size(min = 3, max = 48, message = "username.size")
    val username: String,

    @field:NotBlank(message = "password.empty")
    @field:Size(min = 8, max = 48, message = "password.size")
    val password: String,

    @field:NotNull(message = "role.null")
    val role: UserRole,
)
