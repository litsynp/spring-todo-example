package com.litsynp.todoapp.dto.api.response

import com.litsynp.todoapp.dto.service.response.TodoServiceResponseDto

data class TodoResponseDto(
    var id: Long,
    var title: String,
    var description: String,
) {

    companion object {
        fun of(dto: TodoServiceResponseDto): TodoResponseDto {
            return TodoResponseDto(
                id = dto.id,
                title = dto.title,
                description = dto.description,
            )
        }
    }
}
