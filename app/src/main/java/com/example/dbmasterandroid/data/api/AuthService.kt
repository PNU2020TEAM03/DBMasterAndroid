package com.example.dbmasterandroid.data.api

import com.example.dbmasterandroid.data.dto.ResponseDTO
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/* 이메일 인증 서비스 */
interface AuthService {

    @POST("/v1/auth/request")
    fun authRequest(
            @Body email: HashMap<String, String>
    ): Single<ResponseDTO>

    @POST("/v1/auth/check")
    fun authCheck(
            @Body authInfo: HashMap<String, String>
    ): Single<ResponseDTO>
}