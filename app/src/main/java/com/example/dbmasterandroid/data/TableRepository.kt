package com.example.dbmasterandroid.data

import com.example.dbmasterandroid.data.dto.ResponseDTO
import com.example.dbmasterandroid.data.dto.TableListDTO
import com.example.dbmasterandroid.data.dto.TableRowDataDTO
import io.reactivex.Single
import retrofit2.http.Body

interface TableRepository {

    fun getAllTableList(name: HashMap<String, String>): Single<TableListDTO>

    fun createTable(columnInfo: HashMap<String, String>): Single<ResponseDTO>

    fun checkTableNameValid(name: HashMap<String, String>): Single<ResponseDTO>

    fun getTableInfo(name: HashMap<String, String>): Single<TableRowDataDTO>

    fun searchTableData(keywordInfo: HashMap<String, String>): Single<TableRowDataDTO>

    fun renameTable(tableInfo: HashMap<String, String>): Single<ResponseDTO>

    fun dropTable(tableInfo: HashMap<String, String>): Single<ResponseDTO>

    fun exportTable(tableInfo: HashMap<String, String>): Single<ResponseDTO>

    fun sortTable(tableSortInfo: HashMap<String, String>): Single<TableRowDataDTO>

    fun insertRowData(rowData: HashMap<String, String>): Single<ResponseDTO>

    fun joinTable(tableJoinInfo: HashMap<String, String>): Single<TableRowDataDTO>
}