package uz.thespecialone.weather.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "http://api.openweathermap.org/"

object ApiClient {
    private fun getHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor {
            val requestBuilder = it.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
            val request = requestBuilder.build()
            val response = it.proceed(request)
            response
        }
        return builder.build()
    }

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}


