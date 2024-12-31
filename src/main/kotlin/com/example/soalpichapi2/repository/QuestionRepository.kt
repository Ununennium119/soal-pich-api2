package com.example.soalpichapi2.repository

import com.example.soalpichapi2.model.Question
import org.springframework.data.jpa.repository.Query

interface QuestionRepository : BaseRepository<Question, Long> {
    @Query("SELECT q FROM Question q JOIN q.relatedQuestions r WHERE r = :question")
    fun findQuestionsRelatedTo(question: Question): List<Question>
}
