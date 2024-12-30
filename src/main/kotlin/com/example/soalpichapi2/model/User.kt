package com.example.soalpichapi2.model

import com.example.soalpichapi2.dto.UserDto
import com.example.soalpichapi2.enumeration.UserRole
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false, unique = true)
    private val username: String,

    @Column(nullable = false)
    private var password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private val role: UserRole = UserRole.PLAYER,
) : BaseModel() {
    protected constructor() : this("", "", UserRole.PLAYER)

    fun toDto(): UserDto {
        return UserDto(
            id = id!!,
            username = username,
            password = password,
            role = role,
            createdDate = createdDate,
            modifiedDate = modifiedDate,
        )
    }
}
