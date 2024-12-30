package com.example.soalpichapi2.dto

import com.example.soalpichapi2.enumeration.UserRole
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

@Suppress("unused")
class UserDto(
    val id: Long,
    private val username: String,
    private val password: String,
    val role: UserRole,
    val createdDate: LocalDateTime? = null,
    val modifiedDate: LocalDateTime? = null
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.toString()))
    }

    @JsonIgnore
    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }
}
