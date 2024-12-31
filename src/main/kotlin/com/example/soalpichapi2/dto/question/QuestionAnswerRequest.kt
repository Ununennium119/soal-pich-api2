package com.example.soalpichapi2.dto.question

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class QuestionAnswerRequest(
    @field:NotNull(message = "question.answer.null")
    @field:Min(value = 1, message = "question.answer.min")
    @field:Max(value = 4, message = "question.answer.max")
    val answer: Int,
)
