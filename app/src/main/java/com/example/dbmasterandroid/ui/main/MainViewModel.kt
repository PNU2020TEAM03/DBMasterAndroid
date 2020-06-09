package com.example.dbmasterandroid.ui.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.dbmasterandroid.base.BaseViewModel
import com.example.dbmasterandroid.data.ColumnRepository
import com.example.dbmasterandroid.data.TableRepository
import com.example.dbmasterandroid.data.dto.TableSelectAllDTO
import com.example.dbmasterandroid.utils.PreferenceUtil
import com.example.dbmasterandroid.utils.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(
        private val tableRepository: TableRepository,
        private val columnRepository: ColumnRepository,
        private val context: Context
) : BaseViewModel() {

    private val columnInfoList = ArrayList<HashMap<String, String>>()
    private val tableAllDataList = ArrayList<HashMap<String, String>>()

    private val _columnInfoListUpdateLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val columnInfoListUpdateLiveData: LiveData<Any> get() = _columnInfoListUpdateLiveData

    private val _tableNameLiveData: SingleLiveEvent<String> = SingleLiveEvent()
    val tableNameLiveData: LiveData<String> get() = _tableNameLiveData

    private val _tableAllDataListUpdateLiveData: SingleLiveEvent<Any> = SingleLiveEvent()
    val tableAllDataListUpdateLiveData: LiveData<Any> get() = _tableAllDataListUpdateLiveData

    fun getUserName(): String {
        return PreferenceUtil(context).getName("dbName", "DB Master")
    }

    fun getTableName(): String {
        val tableName = PreferenceUtil(context).getName("tableName", "DB Master")
        _tableNameLiveData.postValue(tableName)
        return tableName
    }

    fun getAllTableData() {
        val name = HashMap<String, String>()

        name["name"] = getUserName()
        name["tableName"] = getTableName()

        if (tableAllDataList.isEmpty()) {
            compositeDisposable.add(columnRepository.getAllTableData(name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        startLoadingIndicator()
                        getTableInfo()
                    }
                    .doOnSuccess { stopLoadingIndicator() }
                    .doOnError { stopLoadingIndicator() }
                    .subscribe({
                        if (it.value != null) {
                            tableAllDataList.addAll(it.value)
                            _tableAllDataListUpdateLiveData.call()
                        } else {
                            _tableAllDataListUpdateLiveData.call()
                        }
                        Log.e("MAIN VIEW MODEL", "$it")
                    }, {
                        it.printStackTrace()
                    })
            )
        } else {
            _tableAllDataListUpdateLiveData.call()
        }
    }

    private fun getTableInfo() {
        val name = HashMap<String, String>()

        name["name"] = getUserName()
        name["tableName"] = getTableName()

        compositeDisposable.add(tableRepository.getTableInfo(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    columnInfoList.addAll(it.value)
                    _columnInfoListUpdateLiveData.call()
                    Log.e("MAIN GET TABLE", "$columnInfoList")
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun getColumnInfoListSize(): Int = columnInfoList.size
    fun getColumnInfoListItem(position: Int): HashMap<String, String> = columnInfoList[position]

    fun getTableDataListSize(): Int = tableAllDataList.size
    fun getTableDataListItem(position: Int): HashMap<String, String> = tableAllDataList[position]
    fun getTableColumnNames(): MutableSet<String> = tableAllDataList[0].keys
}