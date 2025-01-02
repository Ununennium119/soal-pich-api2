package com.example.soalpichapi2.controller

import com.example.soalpichapi2.dto.category.CategoryDto
import com.example.soalpichapi2.dto.category.CategoryCreateUpdateRequest
import com.example.soalpichapi2.enumeration.UserRole
import com.example.soalpichapi2.service.CategoryService
import jakarta.annotation.security.RolesAllowed
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryService: CategoryService,
) {

    @RolesAllowed(UserRole.DESIGNER_VALUE)
    @PostMapping(
        "",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun create(@Valid @RequestBody request: CategoryCreateUpdateRequest): ResponseEntity<CategoryDto> {
        val category = categoryService.create(request)
        return ResponseEntity.ok(category)
    }

    @RolesAllowed(UserRole.DESIGNER_VALUE, UserRole.PLAYER_VALUE)
    @GetMapping(
        "/{id}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun get(@PathVariable id: Long): ResponseEntity<CategoryDto> {
        val category = categoryService.getById(id)
        return category?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }


    @RolesAllowed(UserRole.DESIGNER_VALUE)
    @PutMapping(
        "/{id}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: CategoryCreateUpdateRequest
    ): ResponseEntity<CategoryDto> {
        val category = categoryService.update(id, request)
        return category?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @RolesAllowed(UserRole.DESIGNER_VALUE)
    @DeleteMapping(
        "/{id}",
    )
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        val success = categoryService.deleteById(id)
        return if (success) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @RolesAllowed(UserRole.DESIGNER_VALUE, UserRole.PLAYER_VALUE)
    @GetMapping(
        "",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun list(
        @RequestParam(required = false) page: Int? = null,
        @RequestParam(required = false) pageSize: Int = 10,
        @RequestParam(required = false) order: String? = null,
        @RequestParam(required = false) direction: Direction = Direction.ASC,
        @RequestParam(required = false) title: String? = null,
    ): ResponseEntity<Iterable<CategoryDto>> {
        return if (page == null) {
            val categories = categoryService.list(title)
            ResponseEntity.ok(categories)
        } else {
            val request = if (order != null) {
                PageRequest.of(
                    page,
                    pageSize,
                    Sort.by(direction, order),
                )
            } else {
                PageRequest.of(
                    page,
                    pageSize,
                )
            }
            val categories = categoryService.listWithPage(request, title)
            ResponseEntity.ok(categories)
        }
    }
}
