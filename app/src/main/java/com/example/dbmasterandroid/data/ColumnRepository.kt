package com.example.dbmasterandroid.data

import com.example.dbmasterandroid.data.dto.TableSelectAllDTO
import io.reactivex.Single

interface ColumnRepository {

    fun getAllTableData(name: HashMap<String, String>): Single<TableSelectAllDTO>
}