package com.example.soalpichapi2.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected val id: Long? = null

    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected var createdDate: LocalDateTime? = null

    @LastModifiedDate
    @Column(nullable = false)
    protected var modifiedDate: LocalDateTime? = null
}
