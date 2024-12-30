package com.example.soalpichapi2.repository

import com.example.soalpichapi2.model.User

interface UserRepository : BaseRepository<User, Long> {
    fun findByUsername(username: String): User?
}
