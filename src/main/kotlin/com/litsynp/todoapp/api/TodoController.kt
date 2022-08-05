package com.litsynp.todoapp.api

import com.litsynp.todoapp.dto.api.request.TodoCreateDto
import com.litsynp.todoapp.dto.api.request.TodoUpdateDto
import com.litsynp.todoapp.dto.api.response.TodoResponseDto
import com.litsynp.todoapp.dto.service.request.TodoServiceCreateDto
import com.litsynp.todoapp.dto.service.request.TodoServiceUpdateDto
import com.litsynp.todoapp.global.config.Path
import com.litsynp.todoapp.service.TodoService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(path = [Path.TODO_V1], produces = ["application/json"])
class TodoController(
    private val todoService: TodoService
) {

    @PostMapping
    fun create(
        @Valid @RequestBody dto: TodoCreateDto
    ): ResponseEntity<TodoResponseDto> {
        val result = todoService.create(TodoServiceCreateDto.of(dto))
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(TodoResponseDto.of(result))
    }

    @GetMapping
    fun list(
        pageable: Pageable
    ): ResponseEntity<Page<TodoResponseDto>> {
        val response = todoService.findAll(pageable)
            .map(TodoResponseDto::of)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun detail(
        @PathVariable id: Long
    ): ResponseEntity<TodoResponseDto> {
        val response = TodoResponseDto.of(todoService.findById(id))
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody dto: TodoUpdateDto
    ): ResponseEntity<TodoResponseDto> {
        val response = TodoResponseDto.of(todoService.update(id, TodoServiceUpdateDto.of(dto)))
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<Unit> {
        todoService.deleteById(id)
        return ResponseEntity.noContent()
            .build()
    }
}
