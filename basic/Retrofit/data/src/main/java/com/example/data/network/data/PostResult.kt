package com.example.data.data

import com.google.gson.annotations.SerializedName

data class PostResult(
    @SerializedName("userid")
    val userId : Int,
    @SerializedName("id")
    val id : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("body")
    val body : String
)
