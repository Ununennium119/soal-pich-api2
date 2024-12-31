package com.example.soalpichapi2.repository

import com.example.soalpichapi2.dto.scoreboard.ScoreboardEntryDto
import com.example.soalpichapi2.model.Question
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

    fun existsByQuestionAndCreatedBy(question: Question, username: String): Boolean

    @Query(
        """
        SELECT new com.example.soalpichapi2.dto.scoreboard.ScoreboardEntryDto(qa.createdBy, SUM(qa.score)) 
        FROM QuestionAnswer qa 
        GROUP BY qa.createdBy 
        ORDER BY SUM(qa.score) DESC
        """
    )
    fun getScoreboard(): List<ScoreboardEntryDto>
}
