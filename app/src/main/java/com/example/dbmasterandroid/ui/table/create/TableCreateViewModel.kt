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

    private val columnInfoList = ArrayList<ColumnInfoDTO>()

    private val _tableNameValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableNameValid: LiveData<Any> get() = _tableNameValid

    private val _tableNameInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableNameInvalid: LiveData<Any> get() = _tableNameInvalid

    private val _columnNameValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val columnNameValid: LiveData<Any> get() = _columnNameValid

    private val _columnNameInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val columnNameInvalid: LiveData<Any> get() = _columnNameInvalid

    private val _dataTypeSizeValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val dataTypeSizeValid: LiveData<Any> get() = _dataTypeSizeValid

    private val _dataTypeSizeInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val dataTypeSizeInvalid: LiveData<Any> get() = _dataTypeSizeInvalid

    private val _listUpdateLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val listUpdateLiveData: LiveData<Any> get() = _listUpdateLiveData

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
            _dataTypeSizeValid.call()
        }
        else {
            _dataTypeSizeInvalid.call()
        }
    }

    private fun addColumnList(dataType: String?, dataKey: String?, size: Int?) {
        val columnInfoDTO = ColumnInfoDTO(currentColumnName!!, dataType!!, size.toString(), dataKey!!)
        columnInfoList.add(columnInfoDTO)

        _listUpdateLiveData.call()

        Log.e("COLUMN LIST", "$columnInfoList")
    }
}