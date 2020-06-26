package com.example.dbmasterandroid.data

import com.example.dbmasterandroid.data.dto.TableRowDataDTO
import io.reactivex.Single

interface QueryRepository {

    fun queryCustom(queryInfo: HashMap<String, String>): Single<TableRowDataDTO>

}