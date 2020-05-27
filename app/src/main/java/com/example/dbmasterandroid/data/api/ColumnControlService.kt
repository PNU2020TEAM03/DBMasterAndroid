package com.example.dbmasterandroid.data.api

import com.example.dbmasterandroid.data.dto.TableSelectAllDTO
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ColumnControlService {
    @POST("/v1/column/get-all")
    fun getAllTableData(
            @Body name: HashMap<String, String>
    ): Single<TableSelectAllDTO>
}