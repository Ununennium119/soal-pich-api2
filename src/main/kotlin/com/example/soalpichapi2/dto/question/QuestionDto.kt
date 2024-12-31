package com.example.soalpichapi2.dto.question

import com.example.soalpichapi2.dto.category.CategoryDto
import com.example.soalpichapi2.enumeration.Difficulty
import java.time.LocalDateTime

data class QuestionDto(
    val id: Long,
    val title: String,
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val answer: Int,
    val category: CategoryDto?,
    val difficulty: Difficulty,
    val relatedQuestions: List<QuestionLightDto>,
    val createdDate: LocalDateTime? = null,
    val modifiedDate: LocalDateTime? = null,
    val createdBy: String? = null,
    val modifiedBy: String? = null,
)
