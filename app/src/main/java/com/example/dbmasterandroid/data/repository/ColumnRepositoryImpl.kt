package com.example.dbmasterandroid.data.repository

import com.example.dbmasterandroid.data.ColumnRepository
import com.example.dbmasterandroid.data.api.ColumnControlService
import com.example.dbmasterandroid.data.dto.ResponseDTO
import com.example.dbmasterandroid.data.dto.TableSelectAllDTO
import io.reactivex.Single

class ColumnRepositoryImpl(
        private val columnControlService: ColumnControlService
): ColumnRepository {

    override fun getAllTableData(name: HashMap<String, String>): Single<TableSelectAllDTO> {
        return columnControlService.getAllTableData(name)
    }

    override fun deleteData(deleteInfo: HashMap<String, String>): Single<ResponseDTO> {
        return columnControlService.deleteData(deleteInfo)
    }

    override fun updateData(updateInfo: HashMap<String, String>): Single<ResponseDTO> {
        return columnControlService.updateData(updateInfo)
    }

}