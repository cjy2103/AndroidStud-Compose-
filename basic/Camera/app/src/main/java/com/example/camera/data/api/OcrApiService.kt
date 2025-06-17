package com.example.naverocrtest.data.api

import com.example.naverocrtest.data.model.OcrRequest
import com.example.naverocrtest.data.model.OcrResult
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OcrApiService {
    @POST("extract")
    fun extractOcrData(
        @Body request: OcrRequest,
        @Header("x-incizorlens-api-key") apiKey: String = "GrJtr3F0zkScA0zDKQj2xPYzBtaUhe"
    ): Call<OcrResult>
}