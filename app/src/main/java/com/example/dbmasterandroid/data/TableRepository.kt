package com.example.dbmasterandroid.data

import com.example.dbmasterandroid.data.dto.ResponseDTO
import com.example.dbmasterandroid.data.dto.TableListDTO
import io.reactivex.Single

interface TableRepository {

    fun getAllTableList(name: HashMap<String, String>): Single<TableListDTO>

    fun createTable(columnInfo: HashMap<String, String>): Single<ResponseDTO>
}