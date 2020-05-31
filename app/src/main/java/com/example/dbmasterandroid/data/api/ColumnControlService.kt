package com.example.dbmasterandroid.data.api

import com.example.dbmasterandroid.data.dto.TableSelectAllDTO
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/* 칼럼 데이터 가져오는 서비스 */
interface ColumnControlService {
    @POST("/v1/column/get-all")
    fun getAllTableData(
            @Body name: HashMap<String, String>
    ): Single<TableSelectAllDTO>
}