package com.litsynp.todoapp.global.error

import com.litsynp.todoapp.global.error.exception.BusinessException
import com.litsynp.todoapp.global.logger
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException


@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    private val log = logger()

    override fun handleMethodArgumentNotValid(
        e: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        log.error("handleMethodArgumentNotValidException ${e.message}")
        val response: ErrorResponse = ErrorResponse.of(
            ErrorCode.INVALID_INPUT_VALUE,
            e.bindingResult
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    override fun handleBindException(
        e: org.springframework.validation.BindException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        log.error("handleBindException ${e}")
        val response: ErrorResponse = ErrorResponse.of(
            ErrorCode.INVALID_INPUT_VALUE,
            e.bindingResult
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatchException(
        e: MethodArgumentTypeMismatchException
    ): ResponseEntity<ErrorResponse> {
        log.error("handleMethodArgumentTypeMismatchException ${e}")
        val response: ErrorResponse = ErrorResponse.of(e)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    override fun handleHttpRequestMethodNotSupported(
        e: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        log.error("handleHttpRequestMethodNotSupportedException ${e.message}")
        val response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED)
        return ResponseEntity(response, HttpStatus.METHOD_NOT_ALLOWED)
    }

    override fun handleHttpMessageNotReadable(
        e: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        log.error("handleHttpMessageNotReadable ${e}")
        val response = ErrorResponse.of(
            message = e.mostSpecificCause.message ?: "",
            code = ErrorCode.MESSAGE_NOT_READABLE
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }


    @ExceptionHandler(ConstraintViolationException::class)
    protected fun handleConstraintViolationException(
        e: ConstraintViolationException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        log.error("handleConstraintViolationException ${e}")
        val response = ErrorResponse.of(
            message = e.message ?: "",
            code = ErrorCode.INVALID_INPUT_VALUE
        )
        return ResponseEntity(
            response,
            HttpStatus.valueOf(ErrorCode.INVALID_INPUT_VALUE.status)
        )
    }

    @ExceptionHandler(AccessDeniedException::class)
    protected fun handleAccessDeniedException(
        e: AccessDeniedException
    ): ResponseEntity<ErrorResponse> {
        log.error("handleAccessDeniedException ${e}")
        val response = ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED)
        return ResponseEntity(
            response,
            HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.status)
        )
    }

    @ExceptionHandler(BusinessException::class)
    protected fun handleBusinessException(e: BusinessException): ResponseEntity<ErrorResponse?> {
        log.error("handleEntityNotFoundException ${e}")
        val errorCode: ErrorCode = e.errorCode
        val response = ErrorResponse(message = e.message ?: "", errorCode = errorCode)
        return ResponseEntity(response, HttpStatus.valueOf(errorCode.status))
    }

    @ExceptionHandler(Exception::class)
    protected fun handleException(e: Exception?): ResponseEntity<ErrorResponse> {
        log.error("handleEntityNotFoundException ${e}")
        val response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR)
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
