package com.example.soalpichapi2.service

import com.example.soalpichapi2.dto.scoreboard.ScoreboardDto
import com.example.soalpichapi2.repository.QuestionAnswerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ScoreboardService(
    private val questionAnswerRepository: QuestionAnswerRepository,
) {

    fun getScoreboard(request: PageRequest): Page<ScoreboardDto> {
        val scoreboardEntries = questionAnswerRepository.getScoreboard()

        val rankedScoreboard = scoreboardEntries.mapIndexed { index, entry ->
            ScoreboardDto(
                rank = index + 1,
                username = entry.username,
                score = entry.score,
            )
        }

        val start = request.offset.toInt()
        val end = (start + request.pageSize).coerceAtMost(rankedScoreboard.size)

        val paginatedList = if (start <= rankedScoreboard.size) rankedScoreboard.subList(start, end) else emptyList()
        return PageImpl(paginatedList, request, rankedScoreboard.size.toLong())
    }
}
