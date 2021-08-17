package com.example.base

import io.ktor.http.*

data class BaseResponse<T>(val data: T?, val message: String?)

sealed class Response() {
    data class SuccessWithData(val data: BaseResponse<*>, val httpStatusCode: HttpStatusCode) : Response()
    data class SuccessWithMessage(val message: String, val httpStatusCode: HttpStatusCode) : Response()
    data class ErrorWithMessage(val message: String, val httpStatusCode: HttpStatusCode) : Response()
}