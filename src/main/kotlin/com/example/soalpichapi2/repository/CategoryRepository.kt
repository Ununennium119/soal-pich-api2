package com.example.soalpichapi2.repository

import com.example.soalpichapi2.model.Category

interface CategoryRepository : BaseRepository<Category, Long> {
    fun findByTitle(title: String): Category?
}
