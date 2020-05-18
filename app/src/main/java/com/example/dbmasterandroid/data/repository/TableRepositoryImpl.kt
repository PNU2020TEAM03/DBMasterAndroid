package com.example.dbmasterandroid.data.repository

import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.data.api.TableControlService
import com.example.dbmasterandroid.data.dto.ResponseDTO
import com.example.dbmasterandroid.data.dto.TableListDTO
import io.reactivex.Single

class TableRepositoryImpl(
        private val tableControlService: TableControlService
): TableRepository {

    override fun getAllTableList(name: HashMap<String, String>): Single<TableListDTO> {
        return tableControlService.getAllTableList(name)
    }

    override fun createTable(columnInfo: HashMap<String, String>): Single<ResponseDTO> {
        return tableControlService.createTable(columnInfo)
    }
}