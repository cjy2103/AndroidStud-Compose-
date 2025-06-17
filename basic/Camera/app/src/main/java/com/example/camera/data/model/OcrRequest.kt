package com.example.naverocrtest.data.model

data class OcrRequest(
    val version: String = "V2",
    val requestId: String,
    val timestamp: Long = 0,
    val lang: String = "ko",
    val images: List<OcrImage>
)