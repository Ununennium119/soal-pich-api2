package com.example.soalpichapi2.controller

import com.example.soalpichapi2.dto.scoreboard.ScoreboardDto
import com.example.soalpichapi2.service.ScoreboardService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/scoreboard")
class ScoreboardController(
    private val scoreboardService: ScoreboardService,
) {

    @GetMapping(
        "",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun scoreboard(
        @RequestParam(required = false) page: Int = 0,
        @RequestParam(required = false) pageSize: Int = 10,
    ): ResponseEntity<Page<ScoreboardDto>> {
        val request = PageRequest.of(
            page,
            pageSize,
        )
        val scoreboard = scoreboardService.getScoreboard(request)
        return ResponseEntity.ok(scoreboard)
    }
}