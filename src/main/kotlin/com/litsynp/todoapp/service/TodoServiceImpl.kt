package com.litsynp.todoapp.service

import com.litsynp.todoapp.dao.TodoRepository
import com.litsynp.todoapp.domain.Todo
import com.litsynp.todoapp.dto.service.request.TodoServiceCreateDto
import com.litsynp.todoapp.dto.service.request.TodoServiceUpdateDto
import com.litsynp.todoapp.dto.service.response.TodoServiceResponseDto
import com.litsynp.todoapp.global.error.exception.NotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoServiceImpl(
    private val todoRepository: TodoRepository
) : TodoService {

    @Transactional
    override fun create(dto: TodoServiceCreateDto): TodoServiceResponseDto {
        return Todo(title = dto.title, description = dto.description)
            .let { todo ->
                todoRepository.save(todo)
                TodoServiceResponseDto.of(todo)
            }
    }

    @Transactional(readOnly = true)
    override fun findAll(pageable: Pageable): Page<TodoServiceResponseDto> {
        return todoRepository.findAll(pageable)
            .map(TodoServiceResponseDto::of);
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): TodoServiceResponseDto {
        val todo = todoRepository.findById(id)
            .orElseThrow { NotFoundException("Todo", id) }
        return TodoServiceResponseDto.of(todo)
    }

    @Transactional
    override fun update(id: Long, dto: TodoServiceUpdateDto): TodoServiceResponseDto {
        val todo = todoRepository.findById(id)
            .orElseThrow { NotFoundException("Todo", id) }
        todo.update(
            title = dto.title,
            description = dto.description
        )
        return TodoServiceResponseDto.of(todo)
    }

    @Transactional
    override fun deleteById(id: Long) {
        if (!todoRepository.existsById(id)) {
            throw NotFoundException("Todo", id)
        }

        todoRepository.deleteById(id)
    }
}
