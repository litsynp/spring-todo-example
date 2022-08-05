package com.litsynp.todoapp.service

import com.litsynp.todoapp.dto.service.request.TodoServiceCreateDto
import com.litsynp.todoapp.dto.service.request.TodoServiceUpdateDto
import com.litsynp.todoapp.dto.service.response.TodoServiceResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TodoService {

    fun create(dto: TodoServiceCreateDto): TodoServiceResponseDto

    fun findAll(pageable: Pageable): Page<TodoServiceResponseDto>

    fun findById(id: Long): TodoServiceResponseDto

    fun update(id: Long, dto: TodoServiceUpdateDto): TodoServiceResponseDto

    fun deleteById(id: Long)
}
