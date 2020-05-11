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

    val columnInfoList = ArrayList<ColumnInfoDTO>()

    private val _tableNameValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableNameValid: LiveData<Any> get() = _tableNameValid

    private val _tableNameInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableNameInvalid: LiveData<Any> get() = _tableNameInvalid

    private val _columnNameValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val columnNameValid: LiveData<Any> get() = _columnNameValid

    private val _columnNameInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val columnNameInvalid: LiveData<Any> get() = _columnNameInvalid

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
}