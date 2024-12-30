package com.example.soalpichapi2.dto.category

import java.time.LocalDateTime

data class CategoryDto(
    val id: Long,
    val title: String,
    val createdDate: LocalDateTime? = null,
    val modifiedDate: LocalDateTime? = null
)
