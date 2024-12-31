package com.example.soalpichapi2.service

import com.example.soalpichapi2.dto.question.QuestionAnswerDto
import com.example.soalpichapi2.dto.question.QuestionAnswerRequest
import com.example.soalpichapi2.dto.question.QuestionCreateUpdateRequest
import com.example.soalpichapi2.dto.question.QuestionDto
import com.example.soalpichapi2.exception.ValidationException
import com.example.soalpichapi2.model.Question
import com.example.soalpichapi2.model.QuestionAnswer
import com.example.soalpichapi2.repository.CategoryRepository
import com.example.soalpichapi2.repository.QuestionAnswerRepository
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
    private val questionAnswerRepository: QuestionAnswerRepository,
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

        val questionsRelatedTo = questionRepository.findAllQuestionsRelatedTo(question)
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

        val username = authenticationService.getCurrentUser()!!.username
        val answeredQuestions = questionAnswerRepository.findAllAnsweredQuestions(username)
        val notAnsweredQuestions = questionRepository.findAllQuestionsByCategoryAndNotIn(
            category,
            answeredQuestions.map { it.toDto().question.id }
        )

        if (notAnsweredQuestions.isEmpty()) return null
        return notAnsweredQuestions.random().toDto()
    }

    fun answer(id: Long, request: QuestionAnswerRequest): QuestionAnswerDto? {
        val question = questionRepository.findByIdOrNull(id)
            ?: return null

        val user = authenticationService.getCurrentUser()!!
        if (questionAnswerRepository.existsByQuestionAndCreatedBy(question, user.username)) {
            throw ValidationException("question.alreadyAnswered")
        }

        val questionDto = question.toDto()
        val score = if (request.answer == questionDto.answer) {
            questionDto.difficulty.score
        } else {
            0
        }

        val questionAnswer = QuestionAnswer(
            question = question,
            answer = request.answer,
            score = score,
        )
        val createdQuestionAnswer = questionAnswerRepository.save(questionAnswer)
        return createdQuestionAnswer.toDto()
    }
}
