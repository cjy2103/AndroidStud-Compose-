package com.example.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    //실제 본인이 접속중인 Ip주소를 확인후 넣어야 함.
    private const val baseUri = "http://Ip주소:포트번호" // 가상디바이스로 할거면 10.0.2.2:포트번호
    private var retrofit : Retrofit? = null

    fun getClient() : Retrofit {
        if(retrofit == null){
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()

            retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUri)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}