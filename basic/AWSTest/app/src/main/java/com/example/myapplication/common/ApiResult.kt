package com.example.myapplication.common


sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()  // ✅ 성공 상태
    data class Failure(val exception: Throwable) : ApiResult<Nothing>()  // ❌ 실패 상태
}