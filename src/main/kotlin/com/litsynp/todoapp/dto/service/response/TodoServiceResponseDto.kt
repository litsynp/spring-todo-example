package com.litsynp.todoapp.dto.service.response

import com.litsynp.todoapp.domain.Todo

data class TodoServiceResponseDto(
    var id: Long,
    var title: String,
    var description: String,
) {

    companion object {
        fun of(entity: Todo): TodoServiceResponseDto {
            return TodoServiceResponseDto(
                id = entity.id!!,
                title = entity.title,
                description = entity.description
            )
        }
    }
}
