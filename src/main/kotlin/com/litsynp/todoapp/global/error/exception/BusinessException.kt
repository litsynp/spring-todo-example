package com.litsynp.todoapp.global.error.exception

import com.litsynp.todoapp.global.error.ErrorCode

open class BusinessException(
    message: String?,
    val errorCode: ErrorCode
) : RuntimeException(message)
