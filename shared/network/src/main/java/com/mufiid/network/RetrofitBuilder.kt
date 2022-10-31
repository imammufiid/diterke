package com.mufiid.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    companion object {
        private const val BASE_URL =
            "https://42a1-175-158-53-144.ap.ngrok.io"
    }

    private fun okHttp(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(logInterceptor)
            .build()
    }

    private fun gson() = GsonBuilder()
        .setLenient()
        .setPrettyPrinting()
        .create()

    fun build(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp())
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .build()
    }
}