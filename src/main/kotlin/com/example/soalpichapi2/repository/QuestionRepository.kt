package com.example.soalpichapi2.repository

import com.example.soalpichapi2.model.Category
import com.example.soalpichapi2.model.Question
import org.springframework.data.jpa.repository.Query

interface QuestionRepository : BaseRepository<Question, Long> {

    @Query(
        """
        SELECT q FROM Question q
            JOIN q.relatedQuestions r 
        WHERE r = :question
        """
    )
    fun findAllQuestionsRelatedTo(question: Question): List<Question>

    @Query(
        """
        SELECT q FROM Question q
        WHERE (:category IS NULL OR q.category = :category)
            AND q.id NOT IN :questionIds
        """
    )
    fun findAllQuestionsByCategoryAndNotIn(category: Category?, questionIds: List<Long>) : List<Question>
}
