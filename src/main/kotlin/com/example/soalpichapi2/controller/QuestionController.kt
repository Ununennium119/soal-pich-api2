package com.example.soalpichapi2.controller

import com.example.soalpichapi2.dto.question.QuestionAnswerDto
import com.example.soalpichapi2.dto.question.QuestionAnswerRequest
import com.example.soalpichapi2.dto.question.QuestionCreateUpdateRequest
import com.example.soalpichapi2.dto.question.QuestionDto
import com.example.soalpichapi2.enumeration.UserRole
import com.example.soalpichapi2.service.QuestionService
import jakarta.annotation.security.RolesAllowed
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/questions")
class QuestionController(
    private val questionService: QuestionService,
) {

    @RolesAllowed(UserRole.DESIGNER_VALUE)
    @PostMapping(
        "",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun create(@Valid @RequestBody request: QuestionCreateUpdateRequest): ResponseEntity<QuestionDto> {
        val question = questionService.create(request)
        return ResponseEntity.ok(question)
    }

    @RolesAllowed(UserRole.DESIGNER_VALUE, UserRole.PLAYER_VALUE)
    @GetMapping(
        "/{id}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun get(@PathVariable id: Long): ResponseEntity<QuestionDto> {
        val question = questionService.getById(id)
        return question?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @RolesAllowed(UserRole.DESIGNER_VALUE)
    @PutMapping(
        "/{id}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: QuestionCreateUpdateRequest,
    ): ResponseEntity<QuestionDto> {
        val question = questionService.update(id, request)
        return question?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @RolesAllowed(UserRole.DESIGNER_VALUE)
    @DeleteMapping(
        "/{id}",
    )
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        val success = questionService.deleteById(id)
        return if (success) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @RolesAllowed(UserRole.DESIGNER_VALUE, UserRole.PLAYER_VALUE)
    @GetMapping(
        "",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun list(
        @RequestParam(required = false) page: Int? = null,
        @RequestParam(required = false) pageSize: Int = 10,
        @RequestParam(required = false) order: String? = null,
        @RequestParam(required = false) direction: Direction = Direction.ASC,
    ): ResponseEntity<Iterable<QuestionDto>> {
        val questions = if (page == null) {
            questionService.list()
        } else {
            val request = if (order != null) {
                PageRequest.of(
                    page,
                    pageSize,
                    Sort.by(direction, order),
                )
            } else {
                PageRequest.of(
                    page,
                    pageSize,
                )
            }
            questionService.listWithPage(request)
        }
        return ResponseEntity.ok(questions)
    }

    @RolesAllowed(UserRole.PLAYER_VALUE)
    @GetMapping(
        "/random",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun random(@RequestParam(required = false) category: Long? = null): ResponseEntity<QuestionDto> {
        val question = questionService.getRandomUnansweredQuestion(category)
        return question?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @RolesAllowed(UserRole.PLAYER_VALUE)
    @PostMapping(
        "/{id}/answer",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun answer(
        @PathVariable id: Long,
        @Valid @RequestBody request: QuestionAnswerRequest,
    ): ResponseEntity<QuestionAnswerDto> {
        val question = questionService.answer(id, request)
        return question?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }
}
