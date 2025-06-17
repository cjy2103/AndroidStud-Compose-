package com.example.naverocrtest.data.model

data class OcrImage(
    val format: String,  // ex: "jpg" or "png"
    val data: String,    // base64 encoded image string
    val name: String     // arbitrary name to identify result
)