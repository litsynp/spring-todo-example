package com.litsynp.todoapp.global.error.exception

import com.litsynp.todoapp.global.error.ErrorCode

class NotFoundException(
    resourceName: String,
    id: Long
) : BusinessException(
    "${resourceName} not found with id = ${id}",
    ErrorCode.ENTITY_NOT_FOUND
)
