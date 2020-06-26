package com.example.dbmasterandroid.data.api

import com.example.dbmasterandroid.data.dto.TableRowDataDTO
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/* 사용자 지정 쿼리문 수행 서비스 */
interface QueryService {

    /* 사용자 지정 SELECT 쿼리 API */
    @POST("/v1/query/custom")
    fun queryCustom(
            @Body queryInfo: HashMap<String, String>
    ): Single<TableRowDataDTO>
}