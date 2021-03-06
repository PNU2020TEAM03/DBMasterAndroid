package com.example.dbmasterandroid.di

import com.example.dbmasterandroid.data.*
import com.example.dbmasterandroid.data.api.*
import com.example.dbmasterandroid.data.repository.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val DB_MASTER_BASE_URL = "https://pnuteam03.herokuapp.com"

val apiModule = module {
    factory { provideOkHttpClient() }

    factory { provideSignUpService(provideDBMasterRetrofit(get())) }
    factory { provideConnectionService(provideDBMasterRetrofit(get())) }
    factory { provideTableControlService(provideDBMasterRetrofit(get())) }
    factory { provideColumnControlService(provideDBMasterRetrofit(get())) }
    factory { provideAuthService(provideDBMasterRetrofit(get())) }
    factory { provideQueryControlService(provideDBMasterRetrofit(get())) }

    single<SignUpRepository> { SignUpRepositoryImpl(get(), get()) }
    single<ConnectionRepository> { ConnectionRepositoryImpl(get()) }
    single<TableRepository> { TableRepositoryImpl(get()) }
    single<ColumnRepository> { ColumnRepositoryImpl(get()) }
    single<QueryRepository> { QueryRepositoryImpl(get()) }
}

fun provideOkHttpClient(): OkHttpClient {
    val httpClientBuilder = OkHttpClient().newBuilder()
    val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    httpClientBuilder.addInterceptor(logging)

    return httpClientBuilder.build()
}

fun provideDBMasterRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(DB_MASTER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}

fun provideAuthService(retrofit: Retrofit): AuthService = retrofit.create(AuthService::class.java)
fun provideSignUpService(retrofit: Retrofit): SignUpService = retrofit.create(SignUpService::class.java)
fun provideConnectionService(retrofit: Retrofit): ConnectionService = retrofit.create(ConnectionService::class.java)
fun provideTableControlService(retrofit: Retrofit): TableControlService = retrofit.create(TableControlService::class.java)
fun provideColumnControlService(retrofit: Retrofit): ColumnControlService = retrofit.create(ColumnControlService::class.java)
fun provideQueryControlService(retrofit: Retrofit): QueryService = retrofit.create(QueryService::class.java)