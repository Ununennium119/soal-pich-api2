package com.example.soalpichapi2.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepository<T, ID> : JpaRepository<T, ID>
