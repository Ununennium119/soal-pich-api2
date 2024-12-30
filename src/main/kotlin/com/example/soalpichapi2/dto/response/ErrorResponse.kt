package com.example.soalpichapi2.dto.response

data class ErrorResponse(
    val message: String,
    val errors: List<ValidationError>
)

data class ValidationError(
    val field: String,
    val message: String,
)
