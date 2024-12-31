package com.example.soalpichapi2.dto.question

data class QuestionLightDto(
    val id: Long,
    val title: String,
    val createdBy: String? = null,
)
