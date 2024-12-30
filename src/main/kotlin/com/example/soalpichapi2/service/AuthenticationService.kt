package com.example.soalpichapi2.service

import com.example.soalpichapi2.dto.UserDto
import com.example.soalpichapi2.dto.request.LoginRequest
import com.example.soalpichapi2.dto.request.RegisterRequest
import com.example.soalpichapi2.exception.ValidationException
import com.example.soalpichapi2.model.User
import com.example.soalpichapi2.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val authenticationManager: AuthenticationManager,
) {

    fun register(request: RegisterRequest): UserDto {
        if (userRepository.findByUsername(request.username) != null) {
            throw ValidationException("username.unique")
        }

        val user = User(
            request.username,
            passwordEncoder.encode(request.password),
            request.role,
        )
        val createdUser = userRepository.save(user)
        return createdUser.toDto()
    }

    fun authenticate(request: LoginRequest): UserDto {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password,
            )
        )
        return userRepository.findByUsername(request.username)?.toDto()
            ?: throw UsernameNotFoundException("")
    }

    fun getCurrentUser(): UserDto? {
        val authentication = SecurityContextHolder.getContext().authentication
        val currentUser = try {
            authentication.principal as UserDto
        } catch (e: Exception) {
            null
        }
        return currentUser
    }
}
