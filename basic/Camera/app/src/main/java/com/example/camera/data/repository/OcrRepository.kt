package com.example.naverocrtest.data.repository

import com.example.camera.util.encodeImageToBase64
import com.example.naverocrtest.data.api.RetrofitClient
import com.example.naverocrtest.data.model.OcrImage
import com.example.naverocrtest.data.model.OcrRequest
import com.example.naverocrtest.data.model.OcrResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.UUID

class OcrRepository {
    fun extractOcr(image: File, onResult: (OcrResult?) -> Unit, onError: (Throwable) -> Unit) {
        val encoded = encodeImageToBase64(image)
        val request = OcrRequest(
            requestId = UUID.randomUUID().toString(),
            images = listOf(OcrImage("jpg", encoded, "demo"))
        )

        RetrofitClient.api.extractOcrData(request).enqueue(object : Callback<OcrResult> {
            override fun onResponse(call: Call<OcrResult>, response: Response<OcrResult>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<OcrResult>, t: Throwable) {
                onError(t)
            }
        })
    }
}