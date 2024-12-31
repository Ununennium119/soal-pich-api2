package com.example.soalpichapi2.model

import com.example.soalpichapi2.dto.question.QuestionAnswerDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "question_answers")
class QuestionAnswer(
    @ManyToOne(optional = false)
    private var question: Question,

    @Column(nullable = false)
    private var answer: Int,

    @Column(nullable = false)
    private var score: Int,
) : BaseModel() {
    fun toDto(): QuestionAnswerDto {
        return QuestionAnswerDto(
            id = id!!,
            question = question.toDto(),
            answer = answer,
            score = score,
            createdDate = createdDate,
            modifiedDate = modifiedDate,
            createdBy = createdBy,
        )
    }
}
