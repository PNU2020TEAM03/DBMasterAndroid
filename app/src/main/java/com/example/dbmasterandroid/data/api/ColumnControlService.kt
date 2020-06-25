package com.example.dbmasterandroid.data.api

import com.example.dbmasterandroid.data.dto.ResponseDTO
import com.example.dbmasterandroid.data.dto.TableSelectAllDTO
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/* 칼럼 데이터 가져오는 서비스 */
interface ColumnControlService {

    /* 테이블 데이터 전부 불러오기 API */
    @POST("/v1/column/get-all")
    fun getAllTableData(
            @Body name: HashMap<String, String>
    ): Single<TableSelectAllDTO>

    /* 테이블 데이터 UPDATE API */
    @POST("/v1/column/update")
    fun updateData(
            @Body updateInfo: HashMap<String, String>
    ): Single<ResponseDTO>

    /* 테이블 데이터 DELETE API */
    @POST("/v1/column/delete")
    fun deleteData(
            @Body deleteInfo: HashMap<String, String>
    ): Single<ResponseDTO>
}