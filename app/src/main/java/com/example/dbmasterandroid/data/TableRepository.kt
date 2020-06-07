package com.example.dbmasterandroid.data

import com.example.dbmasterandroid.data.dto.ResponseDTO
import com.example.dbmasterandroid.data.dto.TableColumnInfoDTO
import com.example.dbmasterandroid.data.dto.TableListDTO
import com.example.dbmasterandroid.data.dto.TableSelectAllDTO
import io.reactivex.Single

interface TableRepository {

    fun getAllTableList(name: HashMap<String, String>): Single<TableListDTO>

    fun createTable(columnInfo: HashMap<String, String>): Single<ResponseDTO>

    fun checkTableNameValid(name: HashMap<String, String>): Single<ResponseDTO>

    fun getTableInfo(name: HashMap<String, String>): Single<TableColumnInfoDTO>

    fun searchTableData(keywordInfo: HashMap<String, String>): Single<TableColumnInfoDTO>

    fun renameTable(tableInfo: HashMap<String, String>): Single<ResponseDTO>
}