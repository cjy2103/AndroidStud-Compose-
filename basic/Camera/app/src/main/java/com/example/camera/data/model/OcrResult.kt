package com.example.naverocrtest.data.model

import com.google.gson.JsonElement

data class OcrResult(
    val result: ResultData?
)

data class ResultData(
    val images: List<OcrImageResult>
)

data class OcrImageResult(
    val result: OcrExtractedResult?
)

data class OcrExtractedResult(
    val cl: List<OcrField>
)

data class OcrField(
    val category: String,
    val value: String,
    val vertices: JsonElement?, // 또는 List<Any>?
    val sub: List<OcrSubField>?
)

data class OcrSubField(
    val category: String,
    val value: String,
    val vertices: JsonElement?  // ← 바뀐 부분
)