package com.example.soalpichapi2.controller

import com.example.soalpichapi2.dto.UserDto
import com.example.soalpichapi2.dto.request.LoginRequest
import com.example.soalpichapi2.dto.request.RegisterRequest
import com.example.soalpichapi2.dto.response.LoginResponse
import com.example.soalpichapi2.service.AuthenticationService
import com.example.soalpichapi2.service.JwtService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
    private val jwtService: JwtService,
) {

    @PostMapping(
        "/login",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val user = authenticationService.authenticate(request)
        val token = jwtService.generateToken(user)
        val response = LoginResponse(
            token,
            jwtService.extractExpiration(token)?.time!!,
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping(
        "/register",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<UserDto> {
        val user = authenticationService.register(request)
        return ResponseEntity.ok(user)
    }
}
