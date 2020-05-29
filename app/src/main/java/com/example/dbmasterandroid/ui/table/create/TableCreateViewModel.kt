package com.example.dbmasterandroid.ui.table.create

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.data.dto.ColumnInfoDTO
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.RegularExpressionUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TableCreateViewModel(
        private val tableRepository: TableRepository,
        private val context: Context
): BaseViewModel() {

    var currentTableName: String? = null
    var currentColumnName: String? = null
    private var primaryKeyCount: Int = 0

    private val columnInfoList = ArrayList<ColumnInfoDTO>()

    private val _tableNameValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableNameValid: LiveData<Any> get() = _tableNameValid

    private val _tableNameInvalid: SingleLiveEvent<String> = SingleLiveEvent()
    val tableNameInvalid: LiveData<String> get() = _tableNameInvalid

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

    private val _tableCreateValid: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableCreateValid: LiveData<Any> get() = _tableCreateValid

    private val _tableCreateInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableCreateInvalid: LiveData<Any> get() = _tableCreateInvalid

    private val _columnListSizeInvalid: SingleLiveEvent<Any> = SingleLiveEvent()
    val columnListSizeInvalid: LiveData<Any> get() = _columnListSizeInvalid

    fun getColumnListSize(): Int = columnInfoList.size

    fun getColumnListItem(position: Int): ColumnInfoDTO = columnInfoList[position]

    fun checkTableNameValid(name: String) {
        val valid = RegularExpressionUtil.validCheck(RegularExpressionUtil.Regex.NAME, name)
        val body = HashMap<String, String>()

        body["name"] = PreferenceUtil(context).getName("dbName", "null")
        body["tableName"] = name

        if (valid) {
            compositeDisposable.add(tableRepository.checkTableNameValid(body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { startLoadingIndicator() }
                    .doOnSuccess { stopLoadingIndicator() }
                    .doOnError { stopLoadingIndicator() }
                    .subscribe({
                        if (it.result == "S01") {
                            _tableNameValid.call()
                            currentTableName = name
                        } else {
                            _tableNameInvalid.postValue(it.message)
                        }
                    }, {
                        it.printStackTrace()
                        _tableNameInvalid.postValue("네트워크에 문제가 있습니다.")
                    })
            )
        } else {
            _tableNameInvalid.postValue("사용할 수 없는 테이블 이름입니다.")
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
        if (getColumnListSize() == 10) {
            _columnListSizeInvalid.call()
        } else {
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
        }
        Log.e("COLUMN LIST", "$columnInfoList")
    }

    fun createTable() {
        val dbName = PreferenceUtil(context).getName("dbName", "null")
        val tableName = currentTableName
        var fieldInfo = ""
        var primaryKeyColName = ""
        for (columnInfo in columnInfoList) {
            fieldInfo += if (columnInfo.columnSize.toInt() != 0) {
                "${columnInfo.columnName} ${columnInfo.columnType}(${columnInfo.columnSize})"
            } else {
                "${columnInfo.columnName} ${columnInfo.columnType}"
            }
            if (columnInfo.columnKey == "PK") {
                primaryKeyColName = columnInfo.columnName
            }
            fieldInfo += ", "
        }
        fieldInfo += "PRIMARY KEY ($primaryKeyColName)"

        Log.e("CREATE TABLE", fieldInfo)

        val paramHash = HashMap<String, String>()
        paramHash["name"] = dbName
        paramHash["tableName"] = tableName!!
        paramHash["fieldInfo"] = fieldInfo

        compositeDisposable.add(tableRepository.createTable(paramHash)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { startLoadingIndicator() }
                .doOnSuccess { stopLoadingIndicator() }
                .doOnError { stopLoadingIndicator() }
                .timeout(5, TimeUnit.SECONDS)
                .subscribe({
                    if (it.result == "E01") {
                        _tableCreateInvalid.call()
                    } else {
                        _tableCreateValid.call()
                    }
                    Log.e("CREATE TABLE SUCCESS!!", "$it")
                }, {
                    _tableCreateInvalid.call()
                    it.printStackTrace()
                    Log.e("CREATE TABLE ERROR!!", it.message!!)
                })
        )
    }
}