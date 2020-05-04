package com.example.dbmasterandroid.data.api

import com.example.dbmasterandroid.data.dto.ResponseDTO
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpService {

    @POST("/dbmasterspringboot-1.0/v1/sign-up/check-name")
    fun checkNameDuplication(
            @Body name: HashMap<String, String>
    ): Single<ResponseDTO>

    @POST("/dbmasterspringboot-1.0/v1/sign-up/request")
    fun signUp(
            @Body user: HashMap<String, String>
    ): Single<ResponseDTO>
}