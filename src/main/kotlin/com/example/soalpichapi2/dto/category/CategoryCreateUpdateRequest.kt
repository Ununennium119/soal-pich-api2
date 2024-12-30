package com.example.soalpichapi2.dto.category

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CategoryCreateUpdateRequest(
    @field:NotBlank(message = "category.title.empty")
    @field:Size(min = 3, max = 48, message = "category.title.size")
    val title: String,
)
