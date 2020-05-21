package com.example.dbmasterandroid.data.repository

import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.data.api.TableControlService
import com.example.dbmasterandroid.data.dto.ResponseDTO
import com.example.dbmasterandroid.data.dto.TableListDTO
import com.example.dbmasterandroid.data.dto.TableSelectAllDTO
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

    override fun getAllTableData(name: HashMap<String, String>): Single<TableSelectAllDTO> {
        return tableControlService.getAllTableData(name)
    }

    override fun checkTableNameValid(name: HashMap<String, String>): Single<ResponseDTO> {
        return tableControlService.checkTableNameValid(name)
    }
}