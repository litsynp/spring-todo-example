package com.litsynp.todoapp.dto.service.request

import com.litsynp.todoapp.dto.api.request.TodoCreateDto

data class TodoServiceCreateDto(
    var title: String,
    var description: String,
) {

    companion object {
        fun of(dto: TodoCreateDto): TodoServiceCreateDto {
            return TodoServiceCreateDto(
                title = dto.title!!,
                description = dto.description!!,
            )
        }
    }
}
