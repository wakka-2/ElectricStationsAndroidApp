package com.khayat.app.util.network

import android.content.Context
import com.app.electricstations.util.SharedPrefsUtil
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceGenerator {
    private val httpClient = OkHttpClient.Builder()
    private lateinit var builder:Retrofit.Builder

    fun getRetrofit(context: Context): Retrofit {
        httpClient.readTimeout(100, TimeUnit.SECONDS)
        httpClient.connectTimeout(100, TimeUnit.SECONDS)
        val gson = GsonBuilder()
            .setLenient()
            .create()

        httpClient.addInterceptor { chain ->
            val x = SharedPrefsUtil.getString(context,"token","")
            val original = chain.request()
            val request = original.newBuilder()
                .header("Content-Type", "application/json")
                .header("token",SharedPrefsUtil.getString(context,"token",""))
                .method(original.method, original.body)
                .build()
            chain.proceed(request.newBuilder().build())
        }
        builder = Retrofit.Builder()
            .baseUrl("http://34.133.15.56:80/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.interceptors().add(interceptor)
        return builder.client(httpClient.build()).build()
    }
}