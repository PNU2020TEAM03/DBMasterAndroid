package com.example.dbmasterandroid.data.api

import com.example.dbmasterandroid.data.dto.ResponseDTO
import com.example.dbmasterandroid.data.dto.TableColumnInfoDTO
import com.example.dbmasterandroid.data.dto.TableListDTO
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

/* 메인 테이블 제어 서비스 */
interface TableControlService {
    
    /* 테이블 생성 API */
    @POST("/v1/table/create")
    fun createTable(
            @Body columnInfo: HashMap<String, String>
    ): Single<ResponseDTO>
    
    /* 테이블 모든 이름 조회 API */
    @POST("/v1/table/all-tables")
    fun getAllTableList(
            @Body name: HashMap<String, String>
    ): Single<TableListDTO>

    /* TODO 테이블에 데이터 INSERT API */
    @POST("/v1/table/insert")
    fun insertRowData(
            @Body rowData: HashMap<String, String>
    ): Single<ResponseDTO>

    /* 테이블 DROP API */
    @POST("/v1/table/drop")
    fun dropTable(
            @Body tableInfo: HashMap<String, String>
    ): Single<ResponseDTO>

    /* 테이블 RENAME API */
    @POST("/v1/table/rename")
    fun renameTable(
            @Body tableInfo: HashMap<String, String>
    ): Single<ResponseDTO>

    /* 테이블 이름 중복검사 API */
    @POST("/v1/table/duplicate")
    fun checkTableNameValid(
            @Body name: HashMap<String, String>
    ): Single<ResponseDTO>

    /* 테이블 내 데이터 검색 API */
    @POST("/v1/table/search")
    fun searchTableData(
            @Body keywordInfo: HashMap<String, String>
    ): Single<TableColumnInfoDTO>

    /* 테이블 데이터 csv 파일로 export 하는 API */
    @POST("/v1/table/export")
    fun exportTable(
            @Body tableInfo: HashMap<String, String>
    ): Single<ResponseDTO>

    /* TODO 테이블 JOIN API */
    @POST("/v1/table/join")
    fun joinTable(
            @Body tableJoinInfo: HashMap<String, String>
    ): Single<TableColumnInfoDTO>

    /* TODO 특정 칼럼 기준 정렬 API */
    @POST("/v1/table/sort")
    fun sortTable(
            @Body tableSortInfo: HashMap<String, String>
    ): Single<TableColumnInfoDTO>

    /* 테이블 정보 받기 API */
    @POST("/v1/table/get-info")
    fun getTableInfo(
            @Body name: HashMap<String, String>
    ): Single<TableColumnInfoDTO>
}