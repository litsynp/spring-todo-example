package com.litsynp.todoapp.dto.api.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class TodoCreateDto(
    @field: NotBlank
    var title: String? = null,

    @field: NotNull
    var description: String? = null,
)
