package com.example.dbmasterandroid.data.api

import com.example.dbmasterandroid.data.dto.TableListDTO
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface TableControlService {

    @POST("/dbmasterspringboot-1.0/v1/table/all-tables")
    fun getAllTableList(
            @Body name: HashMap<String, String>
    ): Single<TableListDTO>

}