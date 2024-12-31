package com.example.soalpichapi2.model

import com.example.soalpichapi2.dto.question.QuestionCreateUpdateRequest
import com.example.soalpichapi2.dto.question.QuestionDto
import com.example.soalpichapi2.dto.question.QuestionLightDto
import com.example.soalpichapi2.enumeration.Difficulty
import jakarta.persistence.*

@Entity
@Table(name = "questions")
class Question(
    @Column(nullable = false)
    private var title: String,

    @Column(nullable = false)
    private var question: String,

    @Column(nullable = false)
    private var option1: String,

    @Column(nullable = false)
    private var option2: String,

    @Column(nullable = false)
    private var option3: String,

    @Column(nullable = false)
    private var option4: String,

    @Column(nullable = false)
    private var answer: Int,

    @ManyToOne(optional = true)
    private var category: Category?,

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private var difficulty: Difficulty,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "related_questions",
        joinColumns = [JoinColumn(name = "question_id")],
        inverseJoinColumns = [JoinColumn(name = "related_question_id")],
    )
    private var relatedQuestions: MutableList<Question>,
) : BaseModel() {
    fun toDto(): QuestionDto {
        return QuestionDto(
            id = id!!,
            title = title,
            question = question,
            option1 = option1,
            option2 = option2,
            option3 = option3,
            option4 = option4,
            answer = answer,
            category = category?.toDto(),
            difficulty = difficulty,
            relatedQuestions = relatedQuestions.map { it.toLightDto() },
            createdDate = createdDate,
            modifiedDate = modifiedDate,
            createdBy = createdBy,
            modifiedBy = modifiedBy,
        )
    }

    fun toLightDto(): QuestionLightDto {
        return QuestionLightDto(
            id = id!!,
            title = title,
            createdBy = createdBy,
        )
    }

    fun update(
        request: QuestionCreateUpdateRequest,
        category: Category?,
        relatedQuestions: MutableList<Question>,
    ) {
        this.title = request.title
        this.question = request.question
        this.option1 = request.option1
        this.option2 = request.option2
        this.option3 = request.option3
        this.option4 = request.option4
        this.answer = request.answer
        this.category = category
        this.difficulty = request.difficulty
        this.relatedQuestions = relatedQuestions
    }

    fun removeRelatedQuestion(question: Question) {
        this.relatedQuestions.removeIf { it == question }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Question) return false

        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
