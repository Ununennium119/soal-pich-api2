package com.example.soalpichapi2.repository

import com.example.soalpichapi2.model.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CategoryRepository : BaseRepository<Category, Long> {
    fun findByTitle(title: String): Category?

    fun findAllByTitleLikeIgnoreCase(title: String): List<Category>

    fun findAllByTitleLikeIgnoreCase(title: String, pageable: Pageable): Page<Category>
}
