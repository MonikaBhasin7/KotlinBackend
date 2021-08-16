package com.example.base

import io.ktor.http.*


sealed class Response() {
    data class SuccessWithData<T>(val data: T, val message: String, val httpStatusCode: HttpStatusCode) : Response()
    data class SuccessWithMessage(val message: String, val httpStatusCode: HttpStatusCode) : Response()
    data class ErrorWithMessage(val message: String, val httpStatusCode: HttpStatusCode) : Response()
}