package com.example.soalpichapi2.repository

import com.example.soalpichapi2.model.QuestionAnswer
import org.springframework.data.jpa.repository.Query

interface QuestionAnswerRepository : BaseRepository<QuestionAnswer, Long> {

    @Query(
        """
        SELECT qa FROM QuestionAnswer qa
        WHERE qa.createdBy = :username
        """
    )
    fun findAllAnsweredQuestions(username: String): List<QuestionAnswer>
}
