package com.example.soalpichapi2.dto.question

import com.example.soalpichapi2.enumeration.Difficulty
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class QuestionCreateUpdateRequest(
    @field:NotBlank(message = "question.title.empty")
    @field:Size(min = 3, max = 48, message = "question.title.size")
    val title: String,

    @field:NotBlank(message = "question.question.empty")
    @field:Size(min = 3, max = 128, message = "question.question.size")
    val question: String,

    @field:NotBlank(message = "question.option.empty")
    @field:Size(min = 3, max = 128, message = "question.option.size")
    val option1: String,

    @field:NotBlank(message = "question.option.empty")
    @field:Size(min = 3, max = 128, message = "question.option.size")
    val option2: String,

    @field:NotBlank(message = "question.option.empty")
    @field:Size(min = 3, max = 128, message = "question.option.size")
    val option3: String,

    @field:NotBlank(message = "question.option.empty")
    @field:Size(min = 3, max = 128, message = "question.option.size")
    val option4: String,

    @field:NotNull(message = "question.answer.null")
    @field:Min(value = 1, message = "question.answer.min")
    @field:Max(value = 4, message = "question.answer.max")
    val answer: Int,

    val category: Long?,

    @field:NotNull(message = "question.difficulty.null")
    val difficulty: Difficulty,

    val relatedQuestions: List<Long>,
)
