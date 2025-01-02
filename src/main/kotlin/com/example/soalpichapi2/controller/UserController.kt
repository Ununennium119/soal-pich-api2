package com.example.soalpichapi2.controller

import com.example.soalpichapi2.enumeration.UserRole
import com.example.soalpichapi2.service.UserService
import jakarta.annotation.security.RolesAllowed
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
) {

    @RolesAllowed(UserRole.PLAYER_VALUE)
    @PostMapping("/{username}/follow")
    fun follow(@PathVariable username: String): ResponseEntity<Unit> {
        userService.follow(username)
        return ResponseEntity.ok().build()
    }
}
