package com.example.soalpichapi2.model

import com.example.soalpichapi2.dto.category.CategoryDto
import com.example.soalpichapi2.dto.category.CategoryCreateUpdateRequest
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "categories")
class Category(
    @Column(nullable = false, unique = true)
    private var title: String,
) : BaseModel() {
    fun toDto(): CategoryDto {
        return CategoryDto(
            id = id!!,
            title = title,
            createdDate = createdDate,
            modifiedDate = modifiedDate,
        )
    }

    fun update(request: CategoryCreateUpdateRequest) {
        this.title = request.title
    }
}
