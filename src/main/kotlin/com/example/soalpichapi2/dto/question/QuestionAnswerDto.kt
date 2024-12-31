package com.example.soalpichapi2.dto.question

import java.time.LocalDateTime

data class QuestionAnswerDto(
    val id: Long,
    val question: QuestionDto,
    val answer: Int,
    val score: Int,
    val createdDate: LocalDateTime? = null,
    val modifiedDate: LocalDateTime? = null,
    val createdBy: String? = null,
    val modifiedBy: String? = null,
)
