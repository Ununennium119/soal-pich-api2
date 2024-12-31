package com.example.soalpichapi2.service

import com.example.soalpichapi2.dto.question.QuestionCreateUpdateRequest
import com.example.soalpichapi2.dto.question.QuestionDto
import com.example.soalpichapi2.exception.ValidationException
import com.example.soalpichapi2.model.Category
import com.example.soalpichapi2.model.Question
import com.example.soalpichapi2.repository.CategoryRepository
import com.example.soalpichapi2.repository.QuestionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QuestionService(
    private val categoryRepository: CategoryRepository,
    private val questionRepository: QuestionRepository,
    private val authenticationService: AuthenticationService,
) {

    fun create(request: QuestionCreateUpdateRequest): QuestionDto {
        val category = request.category?.let {
            categoryRepository.findByIdOrNull(it)
                ?: throw ValidationException("question.category.notFound")
        }
        val relatedQuestions = questionRepository.findAllById(request.relatedQuestions.toMutableList())

        val question = Question(
            request.title,
            request.question,
            request.option1,
            request.option2,
            request.option3,
            request.option4,
            request.answer,
            category,
            request.difficulty,
            relatedQuestions
        )
        val createdCategory = questionRepository.save(question)
        return createdCategory.toDto()
    }

    fun getById(id: Long): QuestionDto? {
        val question = questionRepository.findByIdOrNull(id)
        return question?.toDto()
    }

    fun update(id: Long, request: QuestionCreateUpdateRequest): QuestionDto? {
        val question = questionRepository.findByIdOrNull(id)
            ?: return null

        val category = request.category?.let {
            categoryRepository.findByIdOrNull(it)
                ?: throw ValidationException("question.category.notFound")
        }
        val relatedQuestions = questionRepository.findAllById(request.relatedQuestions.toMutableList())

        question.update(request, category, relatedQuestions)
        val updatedQuestion = questionRepository.save(question)
        return updatedQuestion.toDto()
    }

    @Transactional(readOnly = false)
    fun deleteById(id: Long): Boolean {
        val question = questionRepository.findByIdOrNull(id)
            ?: return false

        val questionsRelatedTo = questionRepository.findQuestionsRelatedTo(question)
        questionsRelatedTo.forEach {
            it.removeRelatedQuestion(question)
        }

        questionRepository.saveAll(questionsRelatedTo)
        questionRepository.delete(question)
        return true
    }

    fun list(): List<QuestionDto> {
        return questionRepository.findAll().map { it.toDto() }
    }

    fun listWithPage(request: PageRequest): Page<QuestionDto> {
        return questionRepository.findAll(request).map { it.toDto() }
    }

    fun getRandomUnansweredQuestion(categoryId: Long? = null): QuestionDto? {
        val category = categoryId?.let {
            categoryRepository.findByIdOrNull(it)
                ?: throw ValidationException("question.category.notFound")
        }

        // TODO: Get random unanswered question

        return null
    }

    fun answer(): QuestionDto? {
        // TODO: Implement answering question
        return null
    }
}
