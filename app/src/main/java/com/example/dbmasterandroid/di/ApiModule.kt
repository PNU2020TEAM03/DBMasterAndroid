package com.example.dbmasterandroid.di

import com.example.dbmasterandroid.data.SignUpRepository
import com.example.dbmasterandroid.data.api.SignUpService
import com.example.dbmasterandroid.data.repository.SignUpRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    factory { provideOkHttpClient() }
    factory { provideSignUpService(provideDBMasterRetrofit(get())) }

    single<SignUpRepository> { SignUpRepositoryImpl(get()) }
}

fun provideOkHttpClient(): OkHttpClient {
    val httpClientBuilder = OkHttpClient().newBuilder()
    val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    httpClientBuilder.addInterceptor(logging)

    return httpClientBuilder.build()
}

fun provideDBMasterRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("http://10.0.2.2:8081")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}

fun provideSignUpService(retrofit: Retrofit): SignUpService = retrofit.create(SignUpService::class.java)