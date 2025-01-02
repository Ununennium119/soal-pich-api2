package com.example.soalpichapi2.controller

import com.example.soalpichapi2.dto.base.ErrorResponse
import com.example.soalpichapi2.dto.base.ValidationError
import com.example.soalpichapi2.exception.ValidationException
import mu.KotlinLogging
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.resource.NoResourceFoundException
import java.util.*

@ControllerAdvice
class GlobalExceptionHandler(private val messageSource: MessageSource) {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidRequestException(ex: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(ex.message ?: getMessage("error.bad-request")!!, listOf())
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidArgumentException(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errors: MutableList<ValidationError> = ArrayList()

        for (fieldError in ex.bindingResult.fieldErrors) {
            errors.add(
                ValidationError(
                    field=fieldError.field,
                    message = getMessage(fieldError.defaultMessage) ?: getMessage("error.validation")!!,
                )
            )
        }

        val errorResponse = ErrorResponse(getMessage("error.validation")!!, errors)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(ex: ValidationException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(getMessage(ex.message) ?: getMessage("error.bad-request")!!, listOf())
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AuthorizationDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleAuthorizationDeniedException(ex: AuthorizationDeniedException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            message = ex.message ?: getMessage("error.access-denied")!!,
            errors = listOf(),
        )
        return ResponseEntity(errorResponse, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleNoResourceFoundException(ex: NoResourceFoundException): ResponseEntity<Unit> {
        return ResponseEntity.notFound().build()
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGeneralException(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error(ex) { "Uncaught exception" }
        val errorResponse = ErrorResponse(
            message = ex.message ?: getMessage("error.server-error")!!,
            errors = listOf(),
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }


    private fun getMessage(code: String?): String? {
        if (code == null) return null
        return try {
            messageSource.getMessage(code, arrayOf(), Locale.ENGLISH)
        } catch (_: Exception) {
            code
        }
    }
}
