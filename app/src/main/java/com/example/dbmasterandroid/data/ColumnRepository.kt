package com.example.dbmasterandroid.data

import com.example.dbmasterandroid.data.dto.ResponseDTO
import com.example.dbmasterandroid.data.dto.TableSelectAllDTO
import io.reactivex.Single
import retrofit2.http.Body

interface ColumnRepository {

    fun getAllTableData(name: HashMap<String, String>): Single<TableSelectAllDTO>

    fun deleteData(deleteInfo: HashMap<String, String>): Single<ResponseDTO>

    fun updateData(updateInfo: HashMap<String, String>): Single<ResponseDTO>
}