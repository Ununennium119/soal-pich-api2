package com.example.soalpichapi2.service

import com.example.soalpichapi2.exception.ValidationException
import com.example.soalpichapi2.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val authenticationService: AuthenticationService,
    private val userRepository: UserRepository,
) {

    fun follow(username: String) {
        val userDto = authenticationService.getCurrentUser()!!
        if (userDto.following.contains(username)) {
            throw ValidationException("follow.alreadyFollowing")
        }

        val user = userRepository.findByUsername(userDto.username)
            ?: throw ValidationException("follow.failed")
        val userToFollow = userRepository.findByUsername(username)
            ?: throw ValidationException("follow.failed")

        user.addFollowing(userToFollow)
        userRepository.save(user)
    }
}
