package com.litsynp.todoapp.global.error

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String,
) {

    INVALID_INPUT_VALUE(400, "C001", "Invalid input value"),

    MESSAGE_NOT_READABLE(400, "C002", "Message not readable"),

    INVALID_TYPE_VALUE(400, "C003", "Invalid type value"),

    ENTITY_NOT_FOUND(404, "C004", "Entity not found"),

    INNER_ENTITY_NOT_FOUND(422, "C005", "Inner entity not found"),

    DUPLICATE_ENTITY(409, "C006", "Entity already exists"),

    INTERNAL_SERVER_ERROR(500, "C007", "Server Error"),

    METHOD_NOT_ALLOWED(405, "C008", "Method not allowed"),

    HANDLE_ACCESS_DENIED(403, "C009", "Access is Denied"),

    ;
}
