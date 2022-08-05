package com.litsynp.todoapp.dto.service.request

import com.litsynp.todoapp.dto.api.request.TodoUpdateDto

data class TodoServiceUpdateDto(
    var title: String,
    var description: String,
) {

    companion object {
        fun of(dto: TodoUpdateDto): TodoServiceUpdateDto {
            return TodoServiceUpdateDto(
                title = dto.title!!,
                description = dto.description!!,
            )
        }
    }
}
