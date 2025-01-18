package com.example.soalpichapi2.repository

import com.example.soalpichapi2.model.Category
import com.example.soalpichapi2.model.Question
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
    fun findAllQuestionsByCategoryAndNotIn(category: Category?, questionIds: List<Long>): List<Question>

    @Query(
        """
            SELECT q FROM Question q 
            WHERE (:title IS NULL OR q.title ILIKE :title) 
                AND (:createdBy IS NULL OR q.createdBy in :createdBy)
                AND (:category IS NULL OR q.category.id = :category)
        """
    )
    fun findAllByTitleAndCategory(
        title: String?,
        category: Int?,
        createdBy: List<String>?,
    ): List<Question>

    @Query(
        """
            SELECT q FROM Question q 
            WHERE (:title IS NULL OR q.title ILIKE :title) 
                AND (:createdBy IS NULL OR q.createdBy in :createdBy)
                AND (:category IS NULL OR q.category.id = :category)
        """
    )
    fun findAllByTitleAndCategory(
        title: String?,
        category: Int?,
        createdBy: List<String>?,
        pageable: Pageable,
    ): Page<Question>
}
