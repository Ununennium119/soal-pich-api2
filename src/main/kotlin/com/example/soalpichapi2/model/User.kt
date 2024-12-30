package com.example.soalpichapi2.model

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
}
