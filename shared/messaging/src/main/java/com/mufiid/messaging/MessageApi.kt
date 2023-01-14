package com.mufiid.messaging

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST

interface MessageApi {
    @POST(POST)
    suspend fun post(
        @Body messageRequest: MessageRequest
    ): Response<FirebaseResponse>

    companion object {
        private const val POST = "/fcm/send"
        private const val BASE_URL = "https://fcm.googleapis.com/"
        private const val SERVER_KEY = ""

        private val header = Interceptor { chain ->
            val newBuilder = chain.request()
                .newBuilder()
                .addHeader("Authorization", "key=$SERVER_KEY")
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(newBuilder)
        }

        private fun okHttp(): OkHttpClient {
            val logInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            return OkHttpClient()
                .newBuilder()
                .addInterceptor(header)
                .addInterceptor(logInterceptor)
                .build()
        }

        private fun gson() = GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .create()

        internal fun create(): MessageApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttp())
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .build()
                .create()
        }
    }

    suspend fun postMessage(token: String, type: String, body: String): Result<Boolean> {
        val api = MessagingProvider.api
        val dataRequest = MessageRequest.DataRequest(type, body)
        val request = MessageRequest(token, dataRequest)
        val response = api.post(request)
        return if (response.isSuccessful) {
            val bodyResponse = response.body()
            val responseSuccess =
                bodyResponse != null && bodyResponse.results?.lastOrNull()?.error == null
            if (responseSuccess) {
                Result.success(true)
            } else {
                val message = bodyResponse?.results?.lastOrNull()?.error.orEmpty()
                Result.failure(Throwable(message))
            }
        } else {
            Result.failure(Throwable("Network error"))
        }
    }
}