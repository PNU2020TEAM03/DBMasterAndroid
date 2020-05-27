package com.example.dbmasterandroid.data.api

import com.example.dbmasterandroid.data.dto.ResponseDTO
import com.example.dbmasterandroid.data.dto.TableColumnInfoDTO
import com.example.dbmasterandroid.data.dto.TableListDTO
import com.example.dbmasterandroid.data.dto.TableSelectAllDTO
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface TableControlService {

    @POST("/v1/table/all-tables")
    fun getAllTableList(
            @Body name: HashMap<String, String>
    ): Single<TableListDTO>

    @POST("/v1/table/create")
    fun createTable(
            @Body columnInfo: HashMap<String, String>
    ): Single<ResponseDTO>

    @POST("/v1/table/duplicate")
    fun checkTableNameValid(
            @Body name: HashMap<String, String>
    ): Single<ResponseDTO>

    @POST("/v1/table/get-info")
    fun getTableInfo(
            @Body name: HashMap<String, String>
    ): Single<TableColumnInfoDTO>
}