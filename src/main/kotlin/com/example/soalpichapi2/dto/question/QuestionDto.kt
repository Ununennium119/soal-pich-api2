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
) {
    fun copy(
        id: Long? = null,
        title: String? = null,
        question: String? = null,
        option1: String? = null,
        option2: String? = null,
        option3: String? = null,
        option4: String? = null,
        answer: Int? = null,
        category: CategoryDto? = null,
        difficulty: Difficulty? = null,
        relatedQuestions: List<QuestionLightDto>? = null,
        createdDate: LocalDateTime? = null,
        modifiedDate: LocalDateTime? = null,
        createdBy: String? = null,
        modifiedBy: String? = null,
    ): QuestionDto {
        return QuestionDto(
            id = id ?: this.id,
            title = title ?: this.title,
            question = question ?: this.question,
            option1 = option1 ?: this.option1,
            option2 = option2 ?: this.option2,
            option3 = option3 ?: this.option3,
            option4 = option4 ?: this.option4,
            answer = answer ?: this.answer,
            category = category ?: this.category,
            difficulty = difficulty ?: this.difficulty,
            relatedQuestions = relatedQuestions ?: this.relatedQuestions,
            createdDate = createdDate ?: this.createdDate,
            modifiedDate = modifiedDate ?: this.modifiedDate,
            createdBy = createdBy ?: this.createdBy,
            modifiedBy = modifiedBy ?: this.modifiedBy,
        )
    }
}
