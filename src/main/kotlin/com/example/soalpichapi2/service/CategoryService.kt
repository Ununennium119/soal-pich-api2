package com.example.soalpichapi2.service

import com.example.soalpichapi2.dto.category.CategoryDto
import com.example.soalpichapi2.dto.category.CategoryCreateUpdateRequest
import com.example.soalpichapi2.exception.ValidationException
import com.example.soalpichapi2.model.Category
import com.example.soalpichapi2.repository.CategoryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
) {

    fun create(request: CategoryCreateUpdateRequest): CategoryDto {
        if (categoryRepository.findByTitle(request.title) != null) {
            throw ValidationException("category.title.unique")
        }

        val category = Category(
            request.title,
        )
        val createdCategory = categoryRepository.save(category)
        return createdCategory.toDto()
    }

    fun getById(id: Long): CategoryDto? {
        val category = categoryRepository.findByIdOrNull(id)
        return category?.toDto()
    }

    fun update(id: Long, request: CategoryCreateUpdateRequest): CategoryDto? {
        val category = categoryRepository.findByIdOrNull(id)
            ?: return null

        val categoryWithSameName = categoryRepository.findByTitle(request.title)
        if (categoryWithSameName != null && categoryWithSameName.toDto().id != id) {
            throw ValidationException("category.title.unique")
        }

        category.update(request)
        val updatedCategory = categoryRepository.save(category)
        return updatedCategory.toDto()
    }

    fun deleteById(id: Long): Boolean {
        val category = categoryRepository.findByIdOrNull(id)
            ?: return false

        categoryRepository.delete(category)
        return true
    }

    fun list(title: String?): List<CategoryDto> {
        return if (title.isNullOrBlank()) {
            categoryRepository.findAll()
        } else {
            categoryRepository.findAllByTitleLikeIgnoreCase("%$title%")
        }.map { it.toDto() }
    }

    fun listWithPage(request: PageRequest, title: String?): Page<CategoryDto> {
        return if (title.isNullOrBlank()) {
            categoryRepository.findAll(request)
        } else {
            categoryRepository.findAllByTitleLikeIgnoreCase("%$title%", request)
        }.map { it.toDto() }
    }
}
