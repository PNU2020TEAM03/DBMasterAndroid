package com.example.dbmasterandroid.ui.table.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.data.dto.ColumnInfoDTO
import com.example.dbmasterandroid.utils.RegularExpressionUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

class TableCreateViewModel(
        private val tableRepository: TableRepository,
        private val compositeDisposable: CompositeDisposable
): ViewModel() {

    var currentTableName: String? = null
    var currentColumnName: String? = null
    var dataTypeSize: Int? = null
    var primaryKeyCount: Int = 0

    private val columnInfoList = ArrayList<ColumnInfoDTO>()

    private val _tableNameValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableNameValid: LiveData<Any> get() = _tableNameValid

    private val _tableNameInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableNameInvalid: LiveData<Any> get() = _tableNameInvalid

    private val _columnNameValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val columnNameValid: LiveData<Any> get() = _columnNameValid

    private val _columnNameInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val columnNameInvalid: LiveData<Any> get() = _columnNameInvalid

    private val _dataTypeSizeInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val dataTypeSizeInvalid: LiveData<Any> get() = _dataTypeSizeInvalid

    private val _listUpdateLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val listUpdateLiveData: LiveData<Any> get() = _listUpdateLiveData

    private val _listUpdateValidLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val listUpdateValidLiveData: LiveData<Any> get() = _listUpdateValidLiveData

    private val _listUpdateInvalidLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val listUpdateInvalidLiveData: LiveData<Any> get() = _listUpdateInvalidLiveData

    fun getColumnListSize(): Int = columnInfoList.size

    fun getColumnListItem(position: Int): ColumnInfoDTO = columnInfoList[position]

    fun checkTableNameValid(name: String) {
        val valid = RegularExpressionUtil.validCheck(RegularExpressionUtil.Regex.NAME, name)

        if (valid) {
            _tableNameValid.call()
            currentTableName = name
            Log.e("Sign Up View Model", "$currentTableName")
        } else {
            _tableNameInvalid.call()
        }
    }

    fun checkColumnNameValid(name: String) {
        val valid = RegularExpressionUtil.validCheck(RegularExpressionUtil.Regex.NAME, name)

        if (valid) {
            _columnNameValid.call()
            currentColumnName = name
        } else {
            _columnNameInvalid.call()
        }
    }

    fun checkColumnCreateValid(dataType: String, dataKey: String, size: Int) {
        if (size in 0..255) {
            addColumnList(dataType, dataKey, size)
        } else {
            _dataTypeSizeInvalid.call()
        }
    }

    fun deleteColumnItem(position: Int) {
        columnInfoList.removeAt(position)
        _listUpdateLiveData.call()
    }

    private fun addColumnList(dataType: String?, dataKey: String?, size: Int?) {
        val columnInfoDTO = ColumnInfoDTO(currentColumnName!!, dataType!!, size.toString(), dataKey!!)
        if (dataKey == "PK") {
            primaryKeyCount++
            if (primaryKeyCount > 1) {
                _listUpdateInvalidLiveData.call()
                primaryKeyCount--
            } else {
                columnInfoList.add(columnInfoDTO)
                _listUpdateLiveData.call()
                _listUpdateValidLiveData.call()
            }
        } else {
            columnInfoList.add(columnInfoDTO)
            _listUpdateLiveData.call()
            _listUpdateValidLiveData.call()
        }

        Log.e("COLUMN LIST", "$columnInfoList")
    }
}