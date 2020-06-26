package com.example.dbmasterandroid.data.repository

import com.example.dbmasterandroid.data.QueryRepository
import com.example.dbmasterandroid.data.api.QueryService
import com.example.dbmasterandroid.data.dto.TableRowDataDTO
import io.reactivex.Single

class QueryRepositoryImpl(
    private val queryService: QueryService
): QueryRepository {

    override fun queryCustom(queryInfo: HashMap<String, String>): Single<TableRowDataDTO> {
        return queryService.queryCustom(queryInfo)
    }

}