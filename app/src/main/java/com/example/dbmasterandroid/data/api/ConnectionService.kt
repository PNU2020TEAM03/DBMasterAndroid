package com.example.dbmasterandroid.data.api

import com.example.dbmasterandroid.data.dto.ConnectionDTO
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ConnectionService {

    @POST("/v1/connection/check")
    fun connectionCheck(
            @Body user: HashMap<String, String>
    ): Single<ConnectionDTO>
}