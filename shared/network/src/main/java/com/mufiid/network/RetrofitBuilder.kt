package com.mufiid.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder(private val tokenizer: Tokenizer) {

    companion object {
        private const val BASE_URL =
            "https://42a1-175-158-53-144.ap.ngrok.io"
    }

    private fun okHttp(isRequiredToken: Boolean): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(logInterceptor)
            .run {
                if (isRequiredToken) {
                    addInterceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                            .header("Authorization", tokenizer.token)
                            .build()
                        chain.proceed(newRequest)
                    }
                } else {
                    this
                }
            }
            .build()
    }

    private fun gson() = GsonBuilder()
        .setLenient()
        .setPrettyPrinting()
        .create()

    fun build(isRequiredToken: Boolean = false): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp(isRequiredToken))
            .addConverterFactory(GsonConverterFactory.create(gson()))
            .build()
    }
}