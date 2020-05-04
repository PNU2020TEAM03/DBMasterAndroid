package com.example.dbmasterandroid.di

import com.example.dbmasterandroid.data.ConnectionRepository
import com.example.dbmasterandroid.data.SignUpRepository
import com.example.dbmasterandroid.data.api.ConnectionService
import com.example.dbmasterandroid.data.api.SignUpService
import com.example.dbmasterandroid.data.repository.ConnectionRepositoryImpl
import com.example.dbmasterandroid.data.repository.SignUpRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val AWS_SERVER_BASE_URL = "http://54.180.95.198:8081"
val EMULATOR_LOCAL_BASE_URL = "http://10.0.2.2:8081"

val apiModule = module {
    factory { provideOkHttpClient() }
    factory { provideSignUpService(provideDBMasterRetrofit(get())) }
    factory { provideConnectionService(provideDBMasterRetrofit(get())) }

    single<SignUpRepository> { SignUpRepositoryImpl(get()) }
    single<ConnectionRepository> { ConnectionRepositoryImpl(get()) }
}

fun provideOkHttpClient(): OkHttpClient {
    val httpClientBuilder = OkHttpClient().newBuilder()
    val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    httpClientBuilder.addInterceptor(logging)

    return httpClientBuilder.build()
}

fun provideDBMasterRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(AWS_SERVER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}

fun provideSignUpService(retrofit: Retrofit): SignUpService = retrofit.create(SignUpService::class.java)
fun provideConnectionService(retrofit: Retrofit): ConnectionService = retrofit.create(ConnectionService::class.java)