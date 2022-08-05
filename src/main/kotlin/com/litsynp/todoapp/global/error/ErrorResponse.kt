package com.litsynp.todoapp.global.error

import org.springframework.util.StringUtils
import org.springframework.validation.BindingResult
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import java.util.stream.Collectors

class ErrorResponse(
    message: String = "",
    errorCode: ErrorCode,
    var errors: List<FieldError> = listOf(),
) {
    var code: String = errorCode.code
    var status: Int = errorCode.status
    var message: String = if (StringUtils.hasText(message)) message else errorCode.message

    companion object {
        fun of(code: ErrorCode, bindingResult: BindingResult): ErrorResponse {
            return ErrorResponse(
                errorCode = code,
                errors = FieldError.of(bindingResult)
            )
        }

        fun of(message: String, code: ErrorCode): ErrorResponse {
            return ErrorResponse(message = message, errorCode = code)
        }

        fun of(code: ErrorCode): ErrorResponse {
            return ErrorResponse(errorCode = code)
        }

        fun of(code: ErrorCode, errors: List<FieldError>): ErrorResponse {
            return ErrorResponse(errorCode = code, errors = errors)
        }

        fun of(e: MethodArgumentTypeMismatchException): ErrorResponse {
            val value = if (e.value == null) "" else e.value.toString()
            val errors = FieldError.of(
                e.name,
                value, e.errorCode
            )
            return ErrorResponse(errorCode = ErrorCode.INVALID_TYPE_VALUE, errors = errors)
        }
    }

    class FieldError(
        var field: String,
        var value: String,
        var reason: String,
    ) {
        companion object {
            fun of(
                field: String,
                value: String,
                reason: String,
            ): List<FieldError> {
                val fieldErrors: MutableList<FieldError> = mutableListOf()
                fieldErrors.add(FieldError(field, value, reason))
                return fieldErrors
            }

            fun of(bindingResult: BindingResult): List<FieldError> {
                return bindingResult.fieldErrors.stream()
                    .map { error ->
                        FieldError(
                            field = error.field,
                            value = error.rejectedValue?.toString() ?: "",
                            reason = error.defaultMessage ?: ""
                        )
                    }
                    .collect(Collectors.toList())
            }
        }
    }
}
