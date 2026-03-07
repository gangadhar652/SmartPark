package com.simats.smartpark.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    // ⚠️ IMPORTANT: Configure BASE_URL based on your testing environment
    // 
    // For Android Emulator: Use "http://10.0.2.2/smartpark/"
    //   10.0.2.2 is a special alias to your host machine's localhost
    //
    // For Physical Device: Use your computer's IP address
    //   1. Find your computer's IP: ipconfig (Windows) or ifconfig (Mac/Linux)
    //   2. Example: "http://192.168.1.100/smartpark/"
    //   3. Make sure your phone and computer are on the same WiFi network
    //
    // TROUBLESHOOTING:
    // - Make sure XAMPP Apache is RUNNING (green in XAMPP Control Panel)
    // - Test in browser: http://localhost/smartpark/login.php
    // - Check files are in: C:\xampp\htdocs\smartpark\
    // - For physical device: Use your computer's IP instead of 10.0.2.2
    private const val BASE_URL = "http://14.139.187.229:8081/oct/spic_732/smartpark/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}

